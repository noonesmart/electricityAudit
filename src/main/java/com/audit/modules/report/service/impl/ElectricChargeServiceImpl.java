package com.audit.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audit.modules.common.utils.Log;
import com.audit.modules.report.dao.ECScareDao;
import com.audit.modules.report.dao.ElectricChargeDao;
import com.audit.modules.report.dao.SingleCarrierChargeDao;
import com.audit.modules.report.entity.ECScare;
import com.audit.modules.report.entity.ElectricChargeDTO;
import com.audit.modules.report.entity.SingleCarrierCharge;
import com.audit.modules.report.service.ElectricChargeService;

/**
 * Created by fangren on 2017/3/7. 电费服务类
 */
@Service
public class ElectricChargeServiceImpl implements ElectricChargeService {

	@Autowired
	private ElectricChargeDao electricChargedao;
	@Autowired
	private SingleCarrierChargeDao singleCarrierChargeDao;
	@Autowired
	private ECScareDao eCScareDao;

	private DecimalFormat df = new DecimalFormat("0.00");
	// 返回结果 关键字 key值
	private StringBuffer keyStrBuffer;

	@Override
	public List<ElectricChargeDTO> getstationECStasticByCityId(String cityId, String year, String month,
			String taxType,Integer auditType) {
		List<ElectricChargeDTO> electricChargeDTOList = new ArrayList<ElectricChargeDTO>();

		List<Map<String, Object>> currentElectricList = null;
		List<Map<String, Object>> preElectricList = null;
		ElectricChargeDTO electricChargeDTO = null;
		// 今年
		String currentRegionId = null;
		String preRegionId = null;
		String currentRegionName = null;
		// 今年电费
		Double currentTotal = null;
		// 去年电费
		Double preTotal = null;
		// 电费增加额
		Double addTotal = null;
		// 电费增加比例
		Double addScale = null;

		Map<String, String> map = null;

		String preYear = Integer.valueOf(year) - 1 + "";
		if (null != cityId && cityId.equals("0")) {
			if (taxType.equals("0")) {
				currentElectricList = electricChargedao.stasticCityYearMoneyAll(year, month,auditType);
				preElectricList = electricChargedao.stasticCityYearMoneyAll(preYear, month,auditType);
			} else if (taxType.equals("1")) {
				currentElectricList = electricChargedao.stasticCityYearMoneyNoTax(year, month,auditType);
				preElectricList = electricChargedao.stasticCityYearMoneyNoTax(preYear, month,auditType);
			}

		} else if (null != cityId && !cityId.equals("0")) {
			if (taxType.equals("0")) {
				currentElectricList = electricChargedao.stasticCountyYearMoneyAll(cityId, year, month,auditType);
				preElectricList = electricChargedao.stasticCountyYearMoneyAll(cityId, year, month,auditType);
			} else if (taxType.equals("1")) {
				currentElectricList = electricChargedao.stasticCountyYearMoneyNoTax(cityId, year, month,auditType);
				preElectricList = electricChargedao.stasticCountyYearMoneyNoTax(cityId, year, month,auditType);
			}

		}

		if (null != currentElectricList && currentElectricList.size() > 0) {
			for (Map<String, Object> currentResultMap : currentElectricList) {
				electricChargeDTO = new ElectricChargeDTO();
				for (String key : currentResultMap.keySet()) {
					Log.debug(key);
					Log.debug(currentResultMap.get(key) + "");
				}

				currentRegionId = currentResultMap.get("REGIONID") + "";
				currentRegionName = currentResultMap.get("REGIONNAME") + "";
				electricChargeDTO.setCityName(currentRegionName);
				electricChargeDTO.setCityId(currentRegionId);

				currentTotal = null != currentResultMap.get("TOTALAMOUNT")
						? Double.valueOf(currentResultMap.get("TOTALAMOUNT") + "") : 0.0;
				map = new HashMap<>();
				keyStrBuffer = new StringBuffer();
				keyStrBuffer.append(year);
				keyStrBuffer.append("年");
				if (month != null) {
					if (!month.equals("1")) {
						keyStrBuffer.append("1-");
					}
					keyStrBuffer.append(month).append("月");
				}
				keyStrBuffer.append("(万元)");
				map.put("keyCurrentTotal", keyStrBuffer.toString());
				map.put("currentTotal", currentTotal + "");
				// 找去年的统计数据
				for (Map<String, Object> preResultMap : preElectricList) {
					preRegionId = preResultMap.get("REGIONID") + "";
					if (preRegionId.equals(currentRegionId) && null != preResultMap.get("TOTALAMOUNT")) {
						preTotal = Double.valueOf(preResultMap.get("TOTALAMOUNT").toString());
						addTotal = currentTotal - preTotal;
						if (null != preTotal && preTotal > 0) {
							addScale = addTotal / preTotal;
						}
					}
				}
				keyStrBuffer = new StringBuffer();
				keyStrBuffer.append(preYear);
				keyStrBuffer.append("年");
				if (month != null) {
					if (!month.equals("1")) {
						keyStrBuffer.append("1-");
					}
					keyStrBuffer.append(month).append("月");
				}
				keyStrBuffer.append("(万元)");

				if (null != preTotal) {
					preTotal = Double.valueOf(df.format(preTotal / 10000));
					map.put("keyPastTotal", keyStrBuffer.toString());
					map.put("pastTotal", preTotal + "");
				} else {
					map.put("keyPastTotal", keyStrBuffer.toString());
					map.put("pastTotal", "0");
				}
				// map.put(FieldConstant.stationECStastic.add.toString(),
				// 502.24);
				// map.put(FieldConstant.stationECStastic.addScale.toString(),
				// 22.46d);

				keyStrBuffer = new StringBuffer();
				keyStrBuffer.append(preYear).append("-").append(year);
				keyStrBuffer.append("年");
				if (month != null) {
					if (!month.equals("1")) {
						keyStrBuffer.append("1-");
					}
					keyStrBuffer.append(month).append("月");
				}
				keyStrBuffer.append("增幅(万元)");
				if (addTotal != null) {
					addTotal = Double.valueOf(df.format(addTotal / 1000));
					map.put("keyAddTotal", keyStrBuffer.toString());
					map.put("addTotal", addTotal + "");
				} else {
					map.put("keyAddTotal", keyStrBuffer.toString());
					map.put("addTotal", "0");
				}

				keyStrBuffer = new StringBuffer();
				keyStrBuffer.append(preYear).append("-").append(year);
				keyStrBuffer.append("年");
				if (month != null) {
					if (!month.equals("1")) {
						keyStrBuffer.append("1-");
					}
					keyStrBuffer.append(month).append("月");
				}
				keyStrBuffer.append("增幅（%）");
				if (addScale != null) {
					addScale = Double.valueOf(df.format(addScale * 100));
					map.put("keyAddRate", keyStrBuffer.toString());
					map.put("addRate", addScale +"");
				} else {
					map.put("keyAddRate", keyStrBuffer.toString());
					map.put("addRate", "0");
				}
				electricChargeDTO.setCityData(map);
				electricChargeDTOList.add(electricChargeDTO);
			}
			// 按增幅排序
			Collections.sort(electricChargeDTOList, new Comparator<ElectricChargeDTO>() {

				@Override
				public int compare(ElectricChargeDTO o1, ElectricChargeDTO o2) {
					Map<String, String> map1 = o1.getCityData();
					Map<String, String> map2 = o2.getCityData();
					String addScaleKey = null;
					for (String key : map1.keySet()) {
						if (key.indexOf("增幅（%）") > 0) {
							addScaleKey = key;
							break;
						}
					}
					if (null != addScaleKey) {
						String add1 = map1.get(addScaleKey) +"";
						String add2 = map2.get(addScaleKey) +"";
						Double double1 = Double.valueOf(add1.replace("%", ""));
						Double double2 = Double.valueOf(add2.replace("%", ""));
						return double1.compareTo(double2);
					} else {
						return 0;
					}
				}
			});
		}
		return electricChargeDTOList;
		// return FieldConstant.parsestationECStasticList(cityId);
	}

	/**
	 * 
	 * Description:
	 */
	@Override
	public List<ElectricChargeDTO> getSCECStasticByCityId(String cityId, String year) {
		List<ElectricChargeDTO> list = new ArrayList<ElectricChargeDTO>();
		List<SingleCarrierCharge> singleCarrierChargeList = singleCarrierChargeDao.findByCodeTypeAndYear(cityId, year);
		
		String regionName = null;//区域名
		String scecCharge = null;// 单载波电费
		String singleMoney = null;// 全省平均单载波电费
		if (null != singleCarrierChargeList) {
			
			for (SingleCarrierCharge singleCarrierCharge : singleCarrierChargeList) {
				regionName = singleCarrierCharge.getRegionName();
				scecCharge = singleCarrierCharge.getScecCharge();
				singleMoney = singleCarrierCharge.getSingleMoney();
				
				if (null != regionName && null != scecCharge && null != singleMoney) {
					ElectricChargeDTO electricChargeDTO = new ElectricChargeDTO();
					Map<String, String> datamap = new HashMap<String, String>();
					electricChargeDTO.setAverage01(singleMoney);
					electricChargeDTO.setCityName(regionName);
					datamap.put("SCEC", scecCharge);
					electricChargeDTO.setCityData(datamap);
					list.add(electricChargeDTO);
				}
				
			}
		}
		return list;
		// return FieldConstant.parseSCECStasticList(cityId);
	}

	@Override
	public List<ElectricChargeDTO> getScaleECStasticByCityId(String cityId, String year) {
		List<ElectricChargeDTO> list = new ArrayList<ElectricChargeDTO>();
		List<ECScare> eCScareList = eCScareDao.findByCodeTypeAndYear(cityId, year);
		String regionName = null;
		String incomeScare = null;
		String costScare = null;
		String avarageIncomeScare = null;
		String avaragecostScare = null;
		
		if (null != eCScareList) {
			for (ECScare eCScare : eCScareList) {
				regionName = eCScare.getRegionName();
				incomeScare = eCScare.getIncomeScare();
				costScare = eCScare.getCostScare();
				avarageIncomeScare = eCScare.getAvarageIncomeScare();
				avaragecostScare = eCScare.getAvarageCostScare();
				if (null != regionName && null != incomeScare && null != costScare && null != avarageIncomeScare
						&& null != avaragecostScare) {
					ElectricChargeDTO electricChargeDTO = new ElectricChargeDTO();
					Map<String, String> datamap = new HashMap<String, String>();
					electricChargeDTO.setAverage01(avarageIncomeScare.replaceAll("%", ""));
					electricChargeDTO.setAverage02(avaragecostScare.replaceAll("%", ""));
					electricChargeDTO.setCityName(regionName);
					datamap.put("income", incomeScare.replaceAll("%", ""));
					datamap.put("expenditure", costScare.replaceAll("%", ""));
					electricChargeDTO.setCityData(datamap);
					list.add(electricChargeDTO);
				}
			}
		}
		return list;
		// return FieldConstant.parseScaleECStasticList(cityId);
	}

}
