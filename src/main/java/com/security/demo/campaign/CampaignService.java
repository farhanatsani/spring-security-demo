package com.security.demo.campaign;

import org.springframework.data.domain.Page;

public interface CampaignService {
    CampaignDTO save(CampaignDTO campaignDTO);
    Page<CampaignDTO> findAll(int page, int size);
}
