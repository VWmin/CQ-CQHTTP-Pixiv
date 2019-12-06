package vwmin.coolq.session;

public class Step {
    private int currentStep;
    private String stepName;
    private boolean isRepeatable;


    public Step(int currentStep, String stepName, boolean isRepeatable) {
        this.currentStep = currentStep;
        this.stepName = stepName;
        this.isRepeatable = isRepeatable;
    }




    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public boolean isRepeatable() {
        return isRepeatable;
    }

    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

    @Override
    public Step clone(){
        return new Step(currentStep, stepName, isRepeatable);
    }
}
