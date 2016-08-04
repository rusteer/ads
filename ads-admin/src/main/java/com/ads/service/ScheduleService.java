package com.ads.service;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ScheduleService {
    @Transactional(readOnly = false)
    public void selectOne() {}
}
