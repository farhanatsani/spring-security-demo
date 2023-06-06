package com.security.demo.campaign.impl;

import com.security.demo.campaign.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;
    @Override
    public CampaignDTO save(CampaignDTO campaignDTO) {
        Campaign campaign = CampaignMapper.toCampaign(campaignDTO);
        campaignRepository.saveAndFlush(campaign);
        return CampaignMapper.toCampaignDTO(campaign);
    }
    @Override
    public Page<CampaignDTO> findAll(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of((page - 1), size);
        Page<Campaign> campaigns = campaignRepository.findAll(pageable);
        return campaigns.map(CampaignMapper::toCampaignDTO);
    }
}
