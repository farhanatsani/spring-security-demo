package com.security.demo.campaign;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignServiceImpl;
    @GetMapping
    public ResponseEntity<?> getCampaigns(int page, int size) {
        Page<CampaignDTO> campaigns = campaignServiceImpl.findAll(page, size);
        return ResponseEntity.ok(campaigns);
    }
    @PostMapping("/provision")
    public ResponseEntity<?> saveProduct(@RequestBody CampaignDTO campaignRequest) {
        CampaignDTO campaign = campaignServiceImpl.save(campaignRequest);
        return ResponseEntity.ok(campaign);
    }
}
