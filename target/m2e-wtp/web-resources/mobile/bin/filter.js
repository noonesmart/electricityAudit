/**
 * Created by admin on 2017年4月16日 14:20:50.
 */


/**
 * 日志操作类型
 */
app.filter('logTypeFilter',['$sce',function($sce){
    return function(type){

        type= type.toUpperCase();

        var str="未知";

        switch(type)
        {
            case "C":
                str="新建操作";
                break;
            case "U":
                str= "更新操作"
                break;
            case "D":
                str= "删除操作"
                break;
            case "S":
                str= "启动流程"
                break;
            case "A":
                str= "审批流程"
                break;
        }

        return str;
    };
}]);

/**
 * 是否系统内置角色
 */
app.filter('isSystemFilter',['$sce',function($sce){
    return function(type){
        return type==1 ? "是":"否"
    };
}]);







// //电费提交状态（0.1. 2. 3.  4. 报销失败（票据错误）
// app.filter('submitFilter',['$sce',function($sce){

//     return function(type){

//         if (type == 0) {
//             str="等待推送财务";
//         }
//         if(type == 1) {
//             str="等待财务报销";
//         }
//         if(type == 2) {
//              str= "报销成功";
//         }
//         if(type == 3) {
//             str= "报销失败（填报错误）";
//         }
//         if(type == 4) {
//             str= "报销失败（票据错误）";
//         }
//         return str;
//     };
// }]);

//电费录入列表 状态（0、等待提交审批 1、审批中 2、审批通过 3、审批驳回 4、报销中 5、报销成功 6、报销失败 7、已撤销 8、等待提交稽核）
app.filter('ElectrictyStatusFilter',['$sce',function($sce){
    return function(type){
        var str="未知";

        switch(type)
        {
            case 0:
                str="等待提交审批 ";
                break;
            case 1:
                str= "审批中";
                break;
            case 2:
                str= "审批通过";
                break;
            case 3:
                str= "审批驳回";
                break;
            case 4:
                str= "报销中";
                break;
            case 5:
                str= "报销成功";
                break;
            case 6:
                str= "报销失败";
                break;
            case 7:
                str= "已撤销";
                break;
            case 8:
                str= "等待提交稽核";
                break;
        }
        return str;
    };
}]);


// //审批状态值(-1、不通过 1、通过)
// app.filter('approveStateFilter',['$sce',function($sce){
//     return function(type){

//       if(type==-1){
//           return "不通过"
//       }

//         return "通过";
//     };
// }]);


//流程状态(-1:审批通过 0:被驳回、 1:等待审批、 2:审批中)
app.filter('flowStateFilter',['$sce',function($sce){
    return function(type){


        var str="未知";

        switch(type)
        {
            case -1:
                str="审批通过";
                break;
            case 0:
                str="被驳回";
                break;
            case 1:
                str= "等待提交审批";
                break;
            case 2:
                str= "审批中";
                break;
        }

        return str;
    };
}]);



//包干(0：包干，1：不包干)
app.filter('isCludFilter',['$sce',function($sce){
    return function(type){


        if(type==0){

            return "包干";
        }


        if(type==1){

            return "不包干";
        }

    };
}]);


//周期()
app.filter('paymentCycleFilter',['$sce',function($sce){
    return function(type){


        if(type==1){

            return "月";
        }


        if(type==3){

            return "季度";
        }

        if(type==6){

            return "半年";
        }

        if(type==12){

            return "年";
        }

    };
}]);
//设备专业()
app.filter('deviceStatusFilter',['$sce',function($sce){
    return function(type){


        if(type==1){

            return "传输设备";
        }


        if(type==2){

            return "无线设备";
        }

        if(type==3){

            return "动力设备";
        }


    };
}]);





//电费缴纳方式(1.代维代缴、2.铁塔代缴、3.自缴)
app.filter('payTypeFilter',['$sce',function($sce){
    return function(type){


        if(type==1){

            return "代维代缴";
        }


        if(type==2){

            return "铁塔代缴";
        }
        if(type==3){

            return "自缴";
        }

    };
}]);



// 推送类型（0.等待报销发起人推送财务 1. 等待推送财务  2. 等待财务报销  3. 报销成功  4. 报销失败  5. 已撤销）
app.filter('pushType',['$sce',function($sce){
    return function(type){
        if(type==0){
            return "等待推送报销发起人";
        }
        if(type==1) {
            return "等待推送财务";
        }
        if(type==2) {
            return "等待财务报销";
        }
        if(type==3) {
            return "报销成功";
        }
        if(type==4) {
            return "报销失败";
        }
        if(type==5) {
            return "已撤销";
        }

    }
}]);




// // 变更类型  （0.合同信息，1.供应商信息，2.供电信息，3.发票信息，4.额定功率，5.报账点，6.其他信息）
// app.filter('changeTypeFilter',['$sce',function($sce){
//     return function(type){
//         if(type==0){
//             return "合同信息";
//         }
//         if(type==1) {
//             return "供应商信息";
//         }
//         if(type==2) {
//             return "供电信息";
//         }
//         if(type==3) {
//             return "发票信息";
//         }
//         if(type==4) {
//             return "额定功率";
//         }
//         if(type==5) {
//             return "报账点";
//         }
//         if(type==6) {
//             return "其他信息";
//         }

//     }
// }]);



// 变更状态  （0:待审批,1：审批通过，2：审批失败）
app.filter('changeStatusFilter',['$sce',function($sce){
    return function(type){
        if(type==0){
            return "待审批";
        }
        if(type==1) {
            return "审批通过";
        }
        if(type==2) {
            return "审批失败";
        }

    }
}]);

/*************************************塔维新增加状态****************************************/

// 站址类型  （1铁塔新建、2.电信存量、3.联通存量、4.移动存量）
app.filter('zzTypeFilter',['$sce',function($sce){
    return function(type){
        if(type==1){
            return "铁塔新建";
        }
        if(type==2) {
            return "电信存量";
        }
        if(type==3) {
            return "联通存量";
        }
        if(type==4) {
            return "移动存量";
        }

    }
}]);


// 电费录入类型  （ 0.已保存 1.已提交  2.已撤销  3.待推送  4.已推送 ） 暂未使用该过滤
app.filter('towerSubmitStatus',['$sce',function($sce){
    return function(type){
        if(type==0){
            return "已保存";
        }
        if(type==1){
            return "已提交";
        }
        if(type==2) {
            return "已撤销";
        }
        if(type==3) {
            return "待推送";
        }
        if(type==4) {
            return "已推送";
        }

    }
}]);
