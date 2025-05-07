package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.ClaimDto;
import com.azid.auth.backend.AZ.Auth.dto.ClaimInfoResponse;
import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.PolicyResponseDto;
import com.azid.auth.backend.AZ.Auth.model.*;
import com.azid.auth.backend.AZ.Auth.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ClaimService {

    @Autowired
    ClaimTypeRepository claimTypeRepository;

    @Autowired
    PolicyRepository policyRepository;

    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    ClaimDocumentRepository claimDocumentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    AwsS3Service awsS3Service;

    @Autowired
    DocumentTypeRepository documentTypeRepository;


    //todo : implement the logic to get the list of claim from the database based on user ID

    //todo : implement the logic to get the detail of claim from the database based on claim ID and user ID

    //implement the logic to submit claim and upload the document to S3 bucket
    public ClaimResponseDto submitClaim(String policyID, String userID, String claimTypeId, List<MultipartFile> document) {

        // Validate the documents against the claim type & document type
        validateDocument(document, claimTypeId);

       // Implement the logic to submit claim and upload the document to S3 bucket
        User user = userService.getUserByUserId(userID);
        String folderName = userID+"_"+user.getUsername()+"_"+policyID;
        awsS3Service.uploadFileToS3(policyID,folderName, document);

        // Save claim information to the database
        ClaimType claimtype = getClaimTypeById(Long.valueOf(claimTypeId));
        Policy policy = getPolicyById(Long.valueOf(policyID));
        Claim claim = new Claim();
        claim.setUser(user);
        claim.setClaimType(claimtype);
        claim.setPolicy(policy);

        LocalDate date = LocalDate.now();
        claim.setClaim_date(date);
        claim.setClaimStatus("Submitted");

        claimRepository.save(claim);

        List<String> urlList = awsS3Service.listFilesByPolicyId(policyID,folderName);
        log.info("List of url link for Policy ID: {} , urlLink:{}",policyID,urlList.toString());

        List<DocumentType> documentTypeId = documentTypeRepository.findByClaimTypeId(claimTypeId);

        List<ClaimDocument> claimDocumentsList = new ArrayList<>();
        List<String> documentList = new ArrayList<>();

        for(DocumentType doc : documentTypeId){

            ClaimDocument claimDocument = new ClaimDocument();
            claimDocument.setClaim(claim);

            for(String url : urlList){
                if(url.contains(doc.getDocumentTypeName())){
                    claimDocument.setDocumentType(doc);
                    claimDocument.setDocumentUrl(url);
                    documentList.add(url);
                }
            }
            claimDocument.setDocumentUpload(date);
            claimDocument.setClaim(claim);

            claimDocumentsList.add(claimDocument);
            claimDocumentRepository.save(claimDocument);
        }

        ClaimResponseDto claimResponseDto = new ClaimResponseDto();

        claimResponseDto.setClaimID(claim.getClaimId());
        claimResponseDto.setPolicyID(policy.getId());
        claimResponseDto.setDocumentList(documentList);
        claimResponseDto.setClaimType(claimtype);
        claimResponseDto.setClaim_date(date);
        claimResponseDto.setClaimStatus("Submitted");


        return claimResponseDto;
    }



    // implement the logic to get the list of claim , claimType and document type; from the database based on user ID and status
    public ClaimInfoResponse getClaimInfoByUserId(String userId) {

       try {
           // Check if user exists
           Optional<User> userOptional = userRepository.findByUserId(userId);
           if (userOptional.isPresent()) {

               log.info("User found: {}", userOptional.get().getUsername());

               //Get policy IDs
               List<Policy> policy = policyRepository.findByUserId(String.valueOf(userId));

               List<String> policyIds = new ArrayList<>();
               for (Policy p : policy) {
                   policyIds.add(String.valueOf(p.getId()));
               }
               log.info("List of policy IDs: {}", policyIds.toString());

               // Get claim type & required documents
               List<Object[]> rows = claimTypeRepository.getClaimTypesWithDocuments();

               Map<Long, ClaimInfoResponse.ClaimPolicyDocument> claimMap = new LinkedHashMap<>();

               for (Object[] row : rows) {
                   Long claimTypeId = (Long) row[0];
                   String claimType = (String) row[1];
                   String claimDescription = (String) row[2];
                   String documentName = (String) row[3];
                   claimMap.computeIfAbsent(claimTypeId, id -> {
                       ClaimInfoResponse.ClaimPolicyDocument doc = new ClaimInfoResponse.ClaimPolicyDocument();
                       doc.setClaimTypeId(id);
                       doc.setClaimTypeName(claimType);
                       doc.setClaimDescription(claimDescription);
                       doc.setRequiredDocuments(new ArrayList<>());
                       return doc;
                   }).getRequiredDocuments().add(documentName);
               }

               ClaimInfoResponse response = new ClaimInfoResponse();
               response.setPolicyId(policyIds);
               response.setClaimPolicyDocument(new ArrayList<>(claimMap.values()));
               return response;
           } else {
               throw new RuntimeException("User not found");
           }
       }catch (Exception e){
           log.error("Error fetching claim info: {}", e.getMessage());
           throw new RuntimeException("Error fetching claim info: " + e.getMessage());
       }


    }


    public ClaimType getClaimTypeById(Long claimTypeId) {
        return claimTypeRepository.findById(claimTypeId).orElse(null);
    }

    public Policy getPolicyById(Long policyId) {
        return policyRepository.findById(policyId).orElse(null);
    }

    public void validateDocument(List<MultipartFile> documents, String ClaimTypeId){

        try {
            // Check if documents are empty
            if (documents == null || documents.isEmpty()) {
                throw new RuntimeException("No documents submitted");
            }
            List<DocumentType> requiredDocs = documentTypeRepository.findByClaimTypeId(ClaimTypeId);

            // Extract submitted document names (assuming filenames represent types)
            Set<String> submittedNames = documents.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .filter(Objects::nonNull)
                    .map(name -> name.substring(0, name.lastIndexOf('.'))) // strip file extension
                    .collect(Collectors.toSet());

            for (DocumentType docType : requiredDocs) {
                if (docType.getIsRequired()) {
                    String expectedName = docType.getDocumentTypeName();
                    if (!submittedNames.contains(expectedName)) {
                        throw new RuntimeException("Missing required document: " + expectedName);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error validating documents: {}", e.getMessage());
            throw new RuntimeException("Error validating documents: " + e.getMessage());
        }

    }



}
