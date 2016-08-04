package com.rot.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.rot.entity.ActivateCount;
import com.rot.repository.mybatis.DeviceRepositoryMyBatis;

@Component
@Transactional(readOnly = true)
public class DeviceService {
    @Autowired
    DeviceRepositoryMyBatis repository;
    private Map<String, Object> composeParams(String fromDate, String toDate, int systemApp ) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(fromDate)) map.put("fromDate", fromDate);
        if (StringUtils.isNotBlank(toDate)) map.put("toDate", toDate);
        if(systemApp>=0) map.put("systemApp", systemApp);
        return map;
    }
    public List<ActivateCount> getStat(String fromDate, String toDate, int systemApp ) {
        return repository.getStat(this.composeParams(fromDate, toDate ,systemApp));
    }
    
}
