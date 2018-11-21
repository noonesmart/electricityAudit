package com.audit.modules.electricity.entity;

import com.audit.modules.common.utils.StringUtils;

/**
 * @author 
 * @Description
 * 电费超标杆信息
 * @date 2017/3/16
 * Copyright (c) 2017, ISoftStone All Right reserved.
 */
public class TowerEleBenchmark {

    public static final int TYPE_POWER_RATING = 1;
    public static final int TYPE_SMART_METER = 2;
    public static final int TYPE_SWITCH_POWER = 3;

    private String id;
    /**稽核单ID*/
    private String tower_electricityId;
    /**稽核单流水号*/
    private String tower_electricitySN;
    /**超标杆类型。1 额定功率；2 智能电表；3 开关电源；*/
    private Integer type;
    /**标杆值*/
    private double benchmark;
    /**超标杆比例*/
    private double overProportion;
    /**原因*/
    private String reason;

    public TowerEleBenchmark(){
        this.id = StringUtils.getUUid();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setBenchmark(double benchmark) {
        this.benchmark = benchmark;
    }

    public void setOverProportion(double overProportion) {
        this.overProportion = overProportion;
    }

    /**
     * 获取类型的显示字符
     * @return
     */
    public String getTypeLabel(){
        String result = "额定功率";
        switch (type) {
            case TYPE_POWER_RATING :
                result = "额定功率";
                break;
            case TYPE_SMART_METER:
                result = "智能电表";
                break;
            case TYPE_SWITCH_POWER:
                result = "开关电源";
                break;
        }

        return result;
    }

    public double getBenchmark() {
        return benchmark;
    }

    public double getOverProportion() {
        return overProportion;
    }

    public String getReason() {
        return reason;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTower_electricityId() {
		return tower_electricityId;
	}

	public void setTower_electricityId(String tower_electricityId) {
		this.tower_electricityId = tower_electricityId;
	}

	public String getTower_electricitySN() {
		return tower_electricitySN;
	}

	public void setTower_electricitySN(String tower_electricitySN) {
		this.tower_electricitySN = tower_electricitySN;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

  
}
