package com.valkyrie.fine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.valkyrie.fine.config.TokenConfig;
import com.valkyrie.fine.model.Fine;
import com.valkyrie.fine.model.FineDTO;
import com.valkyrie.fine.repository.FineRepo;

import java.util.Base64;
import java.util.List;

@Service
public class FineService {

    @Autowired
    TokenConfig config;

    @Autowired
    FineRepo fineRepo;

    public String save(Fine fine) {
        fineRepo.save(fine);
        return "New Fine Saved.";
    }

    public Boolean checkFine(String fineId) {
        if (!fineRepo.existsById(fineId)) {
            return null;
        } else {
            return fineRepo.findById(fineId).get().isPaidStatus();
        }
    }

    public ResponseEntity<List<FineDTO>> getFineByUsername(String userId, String token) {
        userId = userId != null? 
            new String(Base64.getDecoder().decode(userId)) : 
            config.getUsername(token);
        List<Fine> fines = fineRepo.findAllByMemberId(userId);
        List<FineDTO> fineDTOs = fines.stream().map(
            fine -> new FineDTO().setId(fine.getId())
                    .setAmount(fine.getAmount())
                    .setMemberId(fine.getMemberId())
                    .setPaidStatus(fine.isPaidStatus())
                    .setReason(fine.getReason())
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(fineDTOs);
    }


    public String deletefine(String id) {
        fineRepo.updatestatus(new String(Base64.getDecoder().decode(id)));
        fineRepo.deletefine();
        return "Fine statement deleted.";
    }
    
}
