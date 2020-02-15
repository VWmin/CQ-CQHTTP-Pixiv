package vwmin.coolq.function.setu.service;

import vwmin.coolq.function.setu.entity.SetuEntity;
import vwmin.coolq.function.setu.entity.SetuCommandParam;
import vwmin.coolq.service.BaseService;

import java.io.IOException;


public interface SetuService extends BaseService {
    SetuEntity setu(SetuCommandParam... parameters) throws IOException;
}
