package com.goatenvmonitor.service;

public class EnvData {
    private static final long serialVersionUID = 1L;
    private String goatHouseID;
    private String temperature;
    private String humidity;
    private String AmmoniaConcentration;
    private String airFlowRate;

    /**
     * ID
     * @return
     */
    public String getID() {
        return goatHouseID;
    }
    public void setID(String goatHouseID) {
        this.goatHouseID = goatHouseID;
    }
    /**
     * 温度
     * @return
     */
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 湿度
     * @return
     */
    public String  getHumidity() {
        return humidity;
    }
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    /**
     * 氨气浓度
     * @return
     */
    public String getAmmoniaConcentration() {
        return AmmoniaConcentration;
    }
    public void setAmmoniaConcentration(String AmmoniaConcentration) {
        this.AmmoniaConcentration = AmmoniaConcentration;
    }

    /**
     * 空气流速 现在改成 硫化氢浓度
     * @return
     */
    public String getAirFlowRate() {
        return airFlowRate;
    }
    public void setAirFlowRate(String airFlowRate) {
        this.airFlowRate = airFlowRate;
    }

    public EnvData() {
        //TODO Auto-generated constructor stub
    }
    public EnvData(String goatHouseID,String temperature,String humidity,String AmmoniaConcentration,String airFlowRate) {
        super();
        this.goatHouseID = goatHouseID;
        this.temperature = temperature;
        this.humidity = humidity;
        this.AmmoniaConcentration = AmmoniaConcentration;
        this.airFlowRate = airFlowRate;
    }

    @Override
    public String toString() {
        return "环境参数 [温度="+temperature+",湿度="+humidity+",氨气浓度="+AmmoniaConcentration+",硫化氢浓度="+airFlowRate+"]";
    }


}
