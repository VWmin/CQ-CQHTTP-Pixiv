package vwmin.coolq.function.setu;

import vwmin.coolq.function.Command;
import vwmin.coolq.function.setu.entity.SetuEntity;
import vwmin.coolq.function.setu.entity.SetuCommandParam;
import vwmin.coolq.function.setu.service.SetuService;

import java.io.IOException;
import java.util.List;

public class SetuCommand implements Command<SetuEntity> {

    private SetuService setuService;
    private List<SetuCommandParam> parameters;

    public SetuCommand(SetuService setuService){
        this.setuService = setuService;
    }

    public SetuCommand(SetuService setuService, List<SetuCommandParam> parameters){
        this.setuService = setuService;
        this.parameters = parameters;
    }

    public void setParameters(List<SetuCommandParam> parameters){
        this.parameters = parameters;
    }


    @Override
    public SetuEntity execute() throws IOException {
        return setuService.setu(parameters.toArray(new SetuCommandParam[0]));
    }
}
