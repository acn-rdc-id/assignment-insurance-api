package com.azid.auth.backend.AZ.Auth.service;

import com.azid.auth.backend.AZ.Auth.dto.ClaimDto;
import com.azid.auth.backend.AZ.Auth.dto.ClaimInfoResponse;
import com.azid.auth.backend.AZ.Auth.dto.ClaimListResponseDto;
import com.azid.auth.backend.AZ.Auth.dto.ClaimResponseDto;
import com.azid.auth.backend.AZ.Auth.exceptions.ResourceNotFoundException;
import com.azid.auth.backend.AZ.Auth.mapper.ClaimTypeMapper;
import com.azid.auth.backend.AZ.Auth.mapper.PolicyMapper;
import com.azid.auth.backend.AZ.Auth.mapper.UserMapper;
import com.azid.auth.backend.AZ.Auth.model.*;
import com.azid.auth.backend.AZ.Auth.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @Autowired
    PolicyMapper policyMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClaimTypeMapper claimTypeMapper;


    //todo : implement the logic to get the list of claim from the database based on user ID
    public List<ClaimListResponseDto> getAllClaims(String userId) {
        log.info("start userid check {}", userId);
        return claimRepository.findByUserUserId(userId)
                .stream()
                .map(claim -> {
                    ClaimListResponseDto dto = new ClaimListResponseDto();
                    dto.setClaimId(claim.getClaimId());
                    dto.setPolicyId(claim.getPolicy() != null ? claim.getPolicy().getId() : null);
                    dto.setClaim_date(claim.getClaim_date());
                    dto.setClaimStatus(claim.getClaimStatus());
                    dto.setClaimTypeId(claim.getClaimType() != null ? claim.getClaimType().getClaimTypeId() : null);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    //todo : implement the logic to get the detail of claim from the database based on claim ID and user ID
    public ClaimResponseDto getClaimDetailsByClaimId(Long claimId,String userId) {

        ClaimResponseDto claimDto = new ClaimResponseDto();
//        User user = userService.getUserByUserId(userId);
//        claimDto.setUserDto(userMapper.toDto(user));

        ClaimType claimType = claimTypeRepository.getClaimTypeByClaimId(claimId);
        claimDto.setClaimType(claimType);

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        List<ClaimDocument> documentList = claimDocumentRepository.getClaimDocumentByClaimId(claimId);
        if (documentList == null || documentList.isEmpty()) {
            throw new ResourceNotFoundException("Claim Document not found");
        }

        List<Map<String,String>> documentListMap = new ArrayList<>();
        for (ClaimDocument doc : documentList) {
            Map<String,String> documentMap = new HashMap<>();
            documentMap.put("documentName", doc.getDocumentType().getDocumentTypeName());
            documentMap.put("documentUrl", doc.getDocumentUrl());
            documentListMap.add(documentMap);
        }

        claimDto.setDocumentList(documentListMap);
        claimDto.setPolicyID(Long.valueOf(claim.getPolicy().getPolicyNo()));
        claimDto.setClaim_date(claim.getClaim_date());
        claimDto.setClaimStatus(claim.getClaimStatus());

        return claimDto;
    }


    //implement the logic to submit claim and upload the document to S3 bucket
    public ClaimResponseDto submitClaim(String policyID, String userID, String claimTypeId, List<MultipartFile> document) {

        try {
                // Validate the documents against the claim type & document type
                validateDocument(document, claimTypeId);

                // Implement the logic to submit claim and upload the document to S3 bucket
                User user = userService.getUserByUserId(userID);
                if (user == null) {
                    throw new RuntimeException("User not found");
                }


                // Save claim information to the database
                ClaimType claimtype = getClaimTypeById(Long.valueOf(claimTypeId));

                Policy policy = getPolicyById(Long.valueOf(policyID));
                Claim claim = new Claim();
                claim.setUser(user);
                claim.setClaimType(claimtype);
                claim.setPolicy(policy);

                LocalDate date = LocalDate.now();
                claim.setClaim_date(date);
                claim.setClaimStatus("Pending");

                claimRepository.save(claim);

                String folderName = userID + "_" + claim.getClaimId() + "_" + policyID;
                awsS3Service.uploadFileToS3(claim.getClaimId(),policyID, folderName, document);
                List<String> urlList = awsS3Service.listFilesByPolicyId(policyID, folderName);
                log.info("List of url link for Policy ID: {} , urlLink:{}", policyID, urlList.toString());

                List<DocumentType> documentTypeId = documentTypeRepository.findByClaimTypeId(claimTypeId);
                List<Map<String,String>> documentList = new ArrayList<>();

                for (DocumentType doc : documentTypeId) {

                    ClaimDocument claimDocument = new ClaimDocument();
                    claimDocument.setClaim(claim);

                    for (String url : urlList) {
                        if (url.contains(doc.getDocumentTypeName())) {
                            claimDocument.setDocumentType(doc);
                            claimDocument.setDocumentUrl(url);

                            String documentName = url.substring(url.lastIndexOf("/") + 1);
                            String[] parts = documentName.split("_");
                            Map<String,String> documentMap = new HashMap<>();
                            documentMap.put("documentName", parts[2]);
                            documentMap.put("documentUrl", url);
                            documentList.add(documentMap);
                        }
                    }
                    claimDocument.setDocumentUpload(date);
                    claimDocument.setClaim(claim);
                    claimDocumentRepository.save(claimDocument);
                }

                ClaimResponseDto claimResponseDto = new ClaimResponseDto();

                claimResponseDto.setClaimID(claim.getClaimId());
                claimResponseDto.setPolicyID(policy.getId());
                claimResponseDto.setDocumentList(documentList);
                claimResponseDto.setClaimType(claimtype);
                claimResponseDto.setClaim_date(date);
                claimResponseDto.setClaimStatus(claim.getClaimStatus());


                return claimResponseDto;
        }catch (Exception e){
            log.error("Error submitting claim: {}", e.getMessage());
            throw new RuntimeException("Error submitting claim: " + e.getMessage());
        }
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
               log.info("List of policy IDs: {}", policyIds);

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
            log.info("Validating documents for claim type ID: {}", ClaimTypeId);
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
