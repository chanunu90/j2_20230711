package org.zerock.j2.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SocialService {

    String getKakaoEmail(String authCode);

}
