(this["webpackJsonpmy-app"]=this["webpackJsonpmy-app"]||[]).push([[0],{353:function(e,t,a){e.exports=a(464)},358:function(e,t,a){},432:function(e,t,a){},449:function(e,t,a){},457:function(e,t,a){},458:function(e,t,a){},464:function(e,t,a){"use strict";a.r(t);var n=a(4),i=a.n(n),s=a(141),r=a.n(s),l=(a(358),a(359),a(344)),c=a(51),o=a(44),m=a(45),p=a(48),h=a(47),u=a(18),d=(a(432),a(87)),g=a.n(d),b=u.d.Item,v=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).state={list:[],mychecked:!0},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("MachineList componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002"),this.getList()}},{key:"getList",value:function(){var e=this;g.a.get(this.props.server_path+"/statistic/getMachineList").then((function(t){e.setState({list:t.data.terminals})})).catch((function(e){console.log(e)}))}},{key:"accessCtrl",value:function(e,t){var a=this,n=e.mac,i="F"===e.flag.substring(2,3)?"on":"off";g.a.get(this.props.server_path+"/statistic/setNetWorkSwitch?mac="+n+"&act="+i).then((function(e){a.setState({list:e.data.terminals})})).catch((function(e){console.log(e)}))}},{key:"render",value:function(){var e=this;return i.a.createElement("div",null,i.a.createElement(u.g,{size:"lg"}),i.a.createElement(u.d,null,this.state.list.map((function(t,a){var n="F"===t.flag.substring(2,3);return i.a.createElement(b,{key:a},i.a.createElement(u.b,{full:!0},i.a.createElement(u.b.Header,{title:t.name+" ( "+(t.ip?t.ip:"--")+")",thumb:"https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg",extra:i.a.createElement(u.e,{checked:n,onChange:e.accessCtrl.bind(e,t)})}),i.a.createElement(u.b.Body,null,i.a.createElement("div",null,t.mac," ")),i.a.createElement(u.b.Footer,{content:"\u5b9e\u65f6\u4e0b\u8f7d\u901f\u5ea6\uff1a"+t.speed,extra:i.a.createElement("div",null," ","\u5b9e\u65f6\u4e0a\u4f20\u901f\u5ea6\uff1a"+t.upSpeed," ")})))}))))}}]),a}(i.a.Component),E=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){return Object(o.a)(this,a),t.call(this,e)}return Object(m.a)(a,[{key:"render",value:function(){var e=this;return i.a.createElement(u.d.Item,{key:this.props.i},i.a.createElement(u.b,{full:!0},i.a.createElement(u.b.Header,{title:"\u7981\u7528\u65f6\u95f4\u533a\u95f4",thumb:"https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"}),i.a.createElement(u.b.Body,null,i.a.createElement("div",null,i.a.createElement(u.c,{mode:"time",minuteStep:1,value:new Date(this.props.obj.startTime),onChange:function(t){return e.props.onChange(e.props.obj,e.props.i,"startTimeStr",t)}},i.a.createElement(u.d.Item,{arrow:"horizontal"},"\u5f00\u59cb\u7981\u7528\u65f6\u95f4")),i.a.createElement(u.c,{mode:"time",minuteStep:1,value:new Date(this.props.obj.endTime),onChange:function(t){return e.props.onChange(e.props.obj,e.props.i,"endTimeStr",t)}},i.a.createElement(u.d.Item,{arrow:"horizontal"},"\u7ed3\u675f\u7981\u7528\u65f6\u95f4"))))))}}]),a}(i.a.Component),f=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).onChangeTime=function(e,t,a,i){var s=n.formatTime(i);console.log("onChangeTime===",t,i,s),"startTimeStr"===a?(e.startTime=i.getTime(),e.startTimeStr=s):(e.endTime=i.getTime(),e.endTimeStr=s),console.log("this.state.list===",n.state.list),n.setState({list:n.state.list}),n.updateList()},n.formatTime=function(e){var t=function(e){return e<10?"0".concat(e):e},a="".concat(t(e.getHours()),":").concat(t(e.getMinutes()));return"".concat(a)},n.updateList=function(){var e=n.state.list;g()({url:n.props.server_path+"/statistic/updateUnavailableTimeList",method:"post",data:e}).then((function(e){n.setState({list:e.data}),console.log("res.data="+JSON.stringify(e.data))})).catch((function(e){console.log(e)}))},n.state={list:[],mychecked:!0},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("UnavailableTimeList componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002"),this.getList()}},{key:"getList",value:function(){var e=this;g.a.get("http://192.168.16.198:8081/statistic/getUnavailableTimeList").then((function(t){e.setState({list:t.data})})).catch((function(e){console.log(e)}))}},{key:"render",value:function(){var e=this;return i.a.createElement("form",null,i.a.createElement(u.d,{className:"date-picker-list"},this.state.list.map((function(t,a){return console.log("timelist i="+a+",obj="+JSON.stringify(t)),i.a.createElement(E,{key:a,obj:t,onChange:e.onChangeTime})}))))}}]),a}(i.a.Component),y=a(470),T=(a(449),function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).state={list:[]},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("ChartItem componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002"),this.getList()}},{key:"getList",value:function(){var e=this;g.a.get("http://192.168.16.198:8081/statistic/getNetWorkData?startTime=&endTime=&machineName="+this.props.machineName).then((function(t){e.setState({list:t.data})})).catch((function(e){console.log(e)}))}},{key:"render",value:function(){var e=[];this.state.list.map((function(t,a){var n={time:t.timeStr,value:t.net};return e.push(n),0}));var t={data:e,height:400,padding:[40,20,40,20],xField:"time",yField:"value",point:{size:5,shape:"diamond"},label:{style:{stroke:"green",fill:"red"}}};return i.a.createElement("div",{className:"g2"},i.a.createElement("div",null,this.props.machineName),i.a.createElement(y.a,t))}}]),a}(n.Component)),O=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).state={list:[]},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("NetChartList componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002")}},{key:"render",value:function(){return i.a.createElement(u.d,null,i.a.createElement(T,{server_path:this.props.server_path,machineName:"X3-55"}),i.a.createElement(T,{server_path:this.props.server_path,machineName:"CM201-2"}),i.a.createElement(T,{server_path:this.props.server_path,machineName:"ali6s"}),i.a.createElement(T,{server_path:this.props.server_path,machineName:"ali11"}),i.a.createElement(T,{server_path:this.props.server_path,machineName:"alioo15"}),i.a.createElement(T,{server_path:this.props.server_path,machineName:"hlnew50"}))}}]),a}(i.a.Component),j=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;Object(o.a)(this,a);var i="";return(n=t.call(this,e)).props.match&&n.props.match.params&&n.props.match.params.selectedTab&&(i=n.props.match.params.selectedTab,console.log("3.ButtonExample this.props.location.pathname=",n.props.location.pathname),console.log("4.ButtonExample this.props.match =",n.props.match),console.log("5.ButtonExample selectedTab =",i)),n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("Jsdemoc componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002")}},{key:"render",value:function(){return i.a.createElement(u.h,null,i.a.createElement(u.a,null,"default"),i.a.createElement(u.g,null),i.a.createElement(u.a,{disabled:!0},"default disabled"),i.a.createElement(u.g,null),i.a.createElement(u.a,{type:"primary"},"primary"),i.a.createElement(u.g,null),i.a.createElement(u.a,{type:"primary",disabled:!0},"primary disabled"),i.a.createElement(u.g,null),i.a.createElement(u.a,{type:"warning"},"warning"),i.a.createElement(u.g,null),i.a.createElement(u.a,{type:"warning",disabled:!0},"warning disabled"),i.a.createElement(u.g,null),i.a.createElement(u.a,{loading:!0},"loading button"),i.a.createElement(u.g,null),i.a.createElement(u.a,{icon:"check-circle-o"},"with icon"),i.a.createElement(u.g,null),i.a.createElement(u.a,{icon:i.a.createElement("img",{src:"https://gw.alipayobjects.com/zos/rmsportal/jBfVSpDwPbitsABtDDlB.svg",alt:""})},"with custom icon"),i.a.createElement(u.g,null),i.a.createElement(u.a,{icon:"check-circle-o",inline:!0,size:"small",style:{marginRight:"4px"}},"with icon and inline"),i.a.createElement(u.a,{icon:"check-circle-o",inline:!0,size:"small"},"with icon and inline"),i.a.createElement(u.g,null),i.a.createElement(u.g,null),i.a.createElement(u.a,{type:"primary",inline:!0,style:{marginRight:"4px"}},"inline primary"),i.a.createElement(u.a,{type:"ghost",inline:!0,style:{marginRight:"4px"},className:"am-button-borderfix"},"inline ghost"),i.a.createElement(u.g,null),i.a.createElement(u.a,{type:"primary",inline:!0,size:"small",style:{marginRight:"4px"}},"primary"),i.a.createElement(u.a,{type:"primary",inline:!0,size:"small",disabled:!0},"primary disabled"),i.a.createElement(u.g,null),i.a.createElement(u.a,{type:"ghost",inline:!0,size:"small",style:{marginRight:"4px"}},"ghost"),i.a.createElement(u.a,{type:"ghost",inline:!0,size:"small",className:"am-button-borderfix",disabled:!0},"ghost disabled"))}}]),a}(i.a.Component),x=a(53),k=Object(x.a)(),D=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;Object(o.a)(this,a);var i="";return(n=t.call(this,e)).props.match.params&&n.props.match.params.selectedTab&&(i=n.props.match.params.selectedTab,console.log("3.HomePage this.props.location.pathname=",n.props.location.pathname),console.log("4.HomePage this.props.match =",n.props.match),console.log("5.HomePage selectedTab =",i)),n.state={selectedTab:i,hidden:!1,fullScreen:!0},console.log("1.HomePage REACT_APP_BASE_URL=","http://production.xxx.xxx","production"),console.log("2.HomePage REACT_APP_SERVER_PATH=","http://192.168.16.198:8081","production"),console.log("\u751f\u4ea7\u73af\u5883"),n}return Object(m.a)(a,[{key:"renderContent",value:function(e){return"machine-list"===e?i.a.createElement(v,{server_path:"http://192.168.16.198:8081"}):"time-list"===e?i.a.createElement(f,Object.assign({server_path:"http://192.168.16.198:8081"},this.props)):"net-chart-list"===e?i.a.createElement(O,{server_path:"http://192.168.16.198:8081"}):"jsdemoc"===e?i.a.createElement(j,null):void 0}},{key:"render",value:function(){var e=this;return i.a.createElement("div",{style:this.state.fullScreen?{position:"fixed",height:"100%",width:"100%",top:0}:{height:400}},i.a.createElement(u.f,{unselectedTintColor:"#949494",tintColor:"#33A3F4",barTintColor:"white",hidden:this.state.hidden,tabBarPosition:"top"},i.a.createElement(u.f.Item,{title:"\u8def\u7531\u5f00\u5173",key:"Life",icon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://zos.alipayobjects.com/rmsportal/sifuoDUQdAFKAVcFGROC.svg) center center /  21px 21px no-repeat"}}),selectedIcon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://zos.alipayobjects.com/rmsportal/iSrlOTqrKddqbOmlvUfq.svg) center center /  21px 21px no-repeat"}}),selected:"machine-list"===this.state.selectedTab,badge:1,onPress:function(){e.setState({selectedTab:"machine-list"}),k.push("machine-list")},"data-seed":"logId"},this.renderContent("machine-list")),i.a.createElement(u.f.Item,{icon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/BTSsmHkPsQSPTktcXyTV.svg) center center /  21px 21px no-repeat"}}),selectedIcon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/ekLecvKBnRazVLXbWOnE.svg) center center /  21px 21px no-repeat"}}),title:"TV\u5b9a\u65f6",key:"Koubei",badge:"new",selected:"time-list"===this.state.selectedTab,onPress:function(){e.setState({selectedTab:"time-list"}),k.push("time-list")},"data-seed":"logId1"},this.renderContent("time-list")),i.a.createElement(u.f.Item,{icon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/BTSsmHkPsQSPTktcXyTV.svg) center center /  21px 21px no-repeat"}}),selectedIcon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/ekLecvKBnRazVLXbWOnE.svg) center center /  21px 21px no-repeat"}}),title:"\u56fe\u8868\u6d4b\u8bd5",key:"Koubei",badge:"",selected:"net-chart-list"===this.state.selectedTab,onPress:function(){e.setState({selectedTab:"net-chart-list"}),k.push("net-chart-list")},"data-seed":"logId1"},this.renderContent("net-chart-list")),i.a.createElement(u.f.Item,{icon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://zos.alipayobjects.com/rmsportal/psUFoAMjkCcjqtUCNPxB.svg) center center /  21px 21px no-repeat"}}),selectedIcon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://zos.alipayobjects.com/rmsportal/IIRLrXXrFAhXVdhMWgUI.svg) center center /  21px 21px no-repeat"}}),title:"\u5f85\u542f\u7528",key:"Friend",dot:!0,selected:"jsdemoc"===this.state.selectedTab,onPress:function(){e.setState({selectedTab:"jsdemoc"}),k.push("jsdemoc")}},this.renderContent("jsdemoc"))))}}]),a}(i.a.Component),L=(a(457),a(458),function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).state={list:[]},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("NetChartItem componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002"),this.getList()}},{key:"getList",value:function(){var e=this;g.a.get("http://192.168.16.198:8081"+this.props.subUrl+"?startTime="+this.props.startTime+"&endTime="+this.props.endTime+"&machineName="+this.props.machineName).then((function(t){e.setState({list:t.data})})).catch((function(e){console.log(e)}))}},{key:"render",value:function(){var e=[];this.state.list.map((function(t,a){var n={time:t.timeStr,value:t.net};return e.push(n),0}));var t={data:e,height:400,padding:[40,20,40,20],xField:"time",yField:"value",point:{size:5,shape:"diamond"},label:{style:{stroke:"green",fill:"red"}}};return i.a.createElement("div",{className:"g2"},i.a.createElement("div",null,this.props.machineName),i.a.createElement(y.a,t))}}]),a}(n.Component)),C=a(81),w=a.n(C),M=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).state={list:[]},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("OnLineList componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002")}},{key:"render",value:function(){var e=w()().format("YYYYMMDD0000"),t=w()().format("YYYYMMDDHHmm");return i.a.createElement(u.d,null,i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"X3-55"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"CM201-2"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"ali6s"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"ali11"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"alioo15"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"hlnew50"}))}}]),a}(i.a.Component),S=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).state={list:[]},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("Least3HoursNetChartList componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002")}},{key:"render",value:function(){var e=w()().subtract(3,"hours").format("YYYYMMDDHHmm"),t=w()().format("YYYYMMDDHHmm");return e.substring(0,8)!==t.substring(0,8)&&(e=w()().format("YYYYMMDD0000")),i.a.createElement(u.d,null,i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"X3-55"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"CM201-2"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"ali6s"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"ali11"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"alioo15"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"hlnew50"}))}}]),a}(i.a.Component),N=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(o.a)(this,a),(n=t.call(this,e)).state={list:[]},n}return Object(m.a)(a,[{key:"componentDidMount",value:function(){console.log("Least1HoursNetChartList componentDidMount \u8fdb\u6765\u4e86\u3002\u3002\u3002")}},{key:"render",value:function(){var e=w()().subtract(1,"hours").format("YYYYMMDDHHmm"),t=w()().format("YYYYMMDDHHmm");return e.substring(0,8)!==t.substring(0,8)&&(e=w()().format("YYYYMMDD0000")),i.a.createElement(u.d,null,i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"X3-55"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"CM201-2"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"ali6s"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"ali11"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"alioo15"}),i.a.createElement(L,{subUrl:"/statistic/getOnLineData",startTime:e,endTime:t,machineName:"hlnew50"}))}}]),a}(i.a.Component),P=Object(x.a)(),z=function(e){Object(p.a)(a,e);var t=Object(h.a)(a);function a(e){var n;Object(o.a)(this,a);var i="online-list";return(n=t.call(this,e)).props.match.params&&n.props.match.params.selectedTab&&(i=n.props.match.params.selectedTab,console.log("1.OnLinePage this.props.location.pathname=",n.props.location.pathname),console.log("2.OnLinePage this.props.match =",n.props.match),console.log("3.OnLinePage selectedTab =",i)),n.state={selectedTab:i,hidden:!1,fullScreen:!0},console.log("\u751f\u4ea7\u73af\u5883 REACT_APP_SERVER_PATH:http://192.168.16.198:8081"),n}return Object(m.a)(a,[{key:"renderContent",value:function(e){return"online-list"===e?i.a.createElement(M,null):"least3-online-list"===e?i.a.createElement(S,null):"least1-online-list"===e?i.a.createElement(N,null):void 0}},{key:"render",value:function(){var e=this;return i.a.createElement("div",{style:this.state.fullScreen?{position:"fixed",height:"100%",width:"100%",top:0}:{height:400}},i.a.createElement(u.f,{unselectedTintColor:"#949494",tintColor:"#33A3F4",barTintColor:"white",hidden:this.state.hidden,tabBarPosition:"top"},i.a.createElement(u.f.Item,{title:"\u8054\u7f51\u65f6\u95f4",key:"Life",icon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://zos.alipayobjects.com/rmsportal/sifuoDUQdAFKAVcFGROC.svg) center center /  21px 21px no-repeat"}}),selectedIcon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://zos.alipayobjects.com/rmsportal/iSrlOTqrKddqbOmlvUfq.svg) center center /  21px 21px no-repeat"}}),selected:"online-list"===this.state.selectedTab,badge:1,onPress:function(){e.setState({selectedTab:"online-list"}),P.push("online-list")},"data-seed":"logId"},this.renderContent("online-list")),i.a.createElement(u.f.Item,{icon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/BTSsmHkPsQSPTktcXyTV.svg) center center /  21px 21px no-repeat"}}),selectedIcon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/ekLecvKBnRazVLXbWOnE.svg) center center /  21px 21px no-repeat"}}),title:"\u6700\u8fd13\u5c0f\u65f6",key:"Koubei",badge:"new",selected:"least3-online-list"===this.state.selectedTab,onPress:function(){e.setState({selectedTab:"least3-online-list"}),P.push("least3-online-list")},"data-seed":"logId1"},this.renderContent("least3-online-list")),i.a.createElement(u.f.Item,{icon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/BTSsmHkPsQSPTktcXyTV.svg) center center /  21px 21px no-repeat"}}),selectedIcon:i.a.createElement("div",{style:{width:"22px",height:"22px",background:"url(https://gw.alipayobjects.com/zos/rmsportal/ekLecvKBnRazVLXbWOnE.svg) center center /  21px 21px no-repeat"}}),title:"\u6700\u8fd11\u5c0f\u65f6",key:"Koubei",badge:"",selected:"least1-online-list"===this.state.selectedTab,onPress:function(){e.setState({selectedTab:"least1-online-list"}),P.push("least1-online-list")},"data-seed":"logId1"},this.renderContent("least1-online-list"))))}}]),a}(i.a.Component);r.a.render(i.a.createElement(l.a,null,i.a.createElement(c.a,{path:"/",exact:!0,component:D}),i.a.createElement(c.a,{path:"/:selectedTab",exact:!0,component:D}),i.a.createElement(c.a,{path:"/online/:selectedTab",exact:!0,component:z}),i.a.createElement(c.a,{path:"/detai/:id",exact:!0,component:j})),document.getElementById("root"))}},[[353,1,2]]]);
//# sourceMappingURL=main.11b432f6.chunk.js.map