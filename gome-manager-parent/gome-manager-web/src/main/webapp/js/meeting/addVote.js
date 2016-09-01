/**
 * Created by caowei on 2015/11/09
 */
//保存商品
try 
{ 

	var auswerAGlobal = ''; 
	var sumAGlobal = ''; 
	var auswerBGlobal = ''; 
	var sumBGlobal = ''; 
	var auswerCGlobal = ''; 
	var sumCGlobal = ''; 
	var auswerDGlobal = ''; 
	var sumDGlobal = ''; 
	var auswerEGlobal = ''; 
	var sumEGlobal = ''; 
	var auswerFGlobal = ''; 
	var sumFGlobal = ''; 

	setInterval("show();",5000);
	function show(){
		var meetId = $("#meetId").val();
		var id = $("#id").val();
		$.ajax({
			url:'../meetingVote/showVoteResult',
			type:'POST',
			dataType:'json',
			data:{
				id:id,
				meetId:meetId
			},
			success:function(data){
				if(data.code==1){
					var questionCode = data.attach.questionCode;
					var code = data.attach.code;
					var voteResultArr = data.attach.voteResultList;
					if(voteResultArr.length==0){
						showResult('','','','','','','','','','','','','');
					}else if(voteResultArr.length==1){
						var auswerA = voteResultArr[0].auswer; 
						var sumA = voteResultArr[0].num; 
						//如果统计结果没变化，不刷新。
						if(auswerA==auswerAGlobal && sumA == sumAGlobal){
							return false;
						}
						auswerAGlobal = auswerA; 
						sumAGlobal = sumA; 
						showResult(questionCode,auswerA,sumA,'','','','','','','','','','');
					}else if(voteResultArr.length==2){
						//两个
						var auswerA = voteResultArr[0].auswer; 
						var sumA = voteResultArr[0].num; 
						var auswerB = voteResultArr[1].auswer; 
						var sumB = voteResultArr[1].num; 
						//如果统计结果没变化，不刷新。
						if(auswerA==auswerAGlobal && sumA == sumAGlobal){
							if(auswerB==auswerBGlobal && sumB == sumBGlobal){
								return false;
							}
						}
						auswerAGlobal = auswerA; 
						sumAGlobal = sumA; 
						auswerBGlobal = auswerB; 
						sumBGlobal = sumB; 
						showResult(questionCode,auswerA,sumA,auswerB,sumB,'','','','','','','','');
					}else if(voteResultArr.length==3){
						//三个
						var auswerA = voteResultArr[0].auswer; 
						var sumA = voteResultArr[0].num; 
						var auswerB = voteResultArr[1].auswer; 
						var sumB = voteResultArr[1].num; 
						var auswerC = voteResultArr[2].auswer; 
						var sumC = voteResultArr[2].num; 
						//如果统计结果没变化，不刷新。
						if(auswerA==auswerAGlobal && sumA == sumAGlobal){
							if(auswerB==auswerBGlobal && sumB == sumBGlobal){
								if(auswerC==auswerCGlobal && sumC == sumCGlobal){
									return false;
								}
							}
						}
						auswerAGlobal = auswerA; 
						sumAGlobal = sumA; 
						auswerBGlobal = auswerB; 
						sumBGlobal = sumB; 
						auswerCGlobal = auswerC; 
						sumCGlobal = sumC; 
						showResult(questionCode,auswerA,sumA,auswerB,sumB,auswerC,sumC,'','','','','','');
					}else if(voteResultArr.length==4){
						//四个
						var auswerA = voteResultArr[0].auswer; 
						var sumA = voteResultArr[0].num; 
						var auswerB = voteResultArr[1].auswer; 
						var sumB = voteResultArr[1].num; 
						var auswerC = voteResultArr[2].auswer; 
						var sumC = voteResultArr[2].num; 
						var auswerD = voteResultArr[3].auswer; 
						var sumD = voteResultArr[3].num; 
						//如果统计结果没变化，不刷新。
						if(auswerA==auswerAGlobal && sumA == sumAGlobal){
							if(auswerB==auswerBGlobal && sumB == sumBGlobal){
								if(auswerC==auswerCGlobal && sumC == sumCGlobal){
									if(auswerD==auswerDGlobal && sumD == sumDGlobal){
										return false;
									}
								}
							}
						}
						auswerAGlobal = auswerA; 
						sumAGlobal = sumA; 
						auswerBGlobal = auswerB; 
						sumBGlobal = sumB; 
						auswerCGlobal = auswerC; 
						sumCGlobal = sumC; 
						auswerDGlobal = auswerD; 
						sumDGlobal = sumD; 
						showResult(questionCode,auswerA,sumA,auswerB,sumB,auswerC,sumC,auswerD,sumD,'','','','');
					}else if(voteResultArr.length==5){
						//五个
						var auswerA = voteResultArr[0].auswer; 
						var sumA = voteResultArr[0].num; 
						var auswerB = voteResultArr[1].auswer; 
						var sumB = voteResultArr[1].num; 
						var auswerC = voteResultArr[2].auswer; 
						var sumC = voteResultArr[2].num; 
						var auswerD = voteResultArr[3].auswer; 
						var sumD = voteResultArr[3].num; 
						var auswerE = voteResultArr[4].auswer; 
						var sumE = voteResultArr[4].num; 
						//如果统计结果没变化，不刷新。
						if(auswerA==auswerAGlobal && sumA == sumAGlobal){
							if(auswerB==auswerBGlobal && sumB == sumBGlobal){
								if(auswerC==auswerCGlobal && sumC == sumCGlobal){
									if(auswerD==auswerDGlobal && sumD == sumDGlobal){
										if(auswerE==auswerEGlobal && sumE == sumEGlobal){
											return false;
										}
									}
								}
							}
						}
						auswerAGlobal = auswerA; 
						sumAGlobal = sumA; 
						auswerBGlobal = auswerB; 
						sumBGlobal = sumB; 
						auswerCGlobal = auswerC; 
						sumCGlobal = sumC; 
						auswerDGlobal = auswerD; 
						sumDGlobal = sumD; 
						auswerEGlobal = auswerE; 
						sumEGlobal = sumE; 
						showResult(questionCode,auswerA,sumA,auswerB,sumB,auswerC,sumC,auswerD,sumD,auswerE,sumE,'','');
					}else if(voteResultArr.length==6){
						//五个
						var auswerA = voteResultArr[0].auswer; 
						var sumA = voteResultArr[0].num; 
						var auswerB = voteResultArr[1].auswer; 
						var sumB = voteResultArr[1].num; 
						var auswerC = voteResultArr[2].auswer; 
						var sumC = voteResultArr[2].num; 
						var auswerD = voteResultArr[3].auswer; 
						var sumD = voteResultArr[3].num; 
						var auswerE = voteResultArr[4].auswer; 
						var sumE = voteResultArr[4].num; 
						var auswerF = voteResultArr[5].auswer; 
						var sumF = voteResultArr[5].num; 
						//如果统计结果没变化，不刷新。
						if(auswerA==auswerAGlobal && sumA == sumAGlobal){
							if(auswerB==auswerBGlobal && sumB == sumBGlobal){
								if(auswerC==auswerCGlobal && sumC == sumCGlobal){
									if(auswerD==auswerDGlobal && sumD == sumDGlobal){
										if(auswerE==auswerEGlobal && sumE == sumEGlobal){
											if(auswerF==auswerFGlobal && sumF == sumFGlobal){
												return false;
											}
										}
									}
								}
							}
						}
						auswerAGlobal = auswerA; 
						sumAGlobal = sumA; 
						auswerBGlobal = auswerB; 
						sumBGlobal = sumB; 
						auswerCGlobal = auswerC; 
						sumCGlobal = sumC; 
						auswerDGlobal = auswerD; 
						sumDGlobal = sumD; 
						auswerEGlobal = auswerE; 
						sumEGlobal = sumE; 
						auswerFGlobal = auswerF; 
						sumFGlobal = sumF; 
						showResult(questionCode,auswerA,sumA,auswerB,sumB,auswerC,sumC,auswerD,sumD,auswerE,sumE,auswerF,sumF);
					}else{

					}
				}
			},
			error:function(){

			}

		});
	}

	var myChart = new JSChart('graph', 'bar',"4949a117e0bff9be30");
	function showResult(quesNum,auswerA,sumA,auswerB,sumB,auswerC,sumC,auswerD,sumD,auswerE,sumE,auswerF,sumF){
		var wideVal = document.getElementById("wide").value;
		wideVal = Number(wideVal);
		var heightVal = document.getElementById("height").value;
		heightVal = Number(heightVal);
		var myData;
		var colors;
		var wightZ;
		var aVal;
		var bVal;
		var cVal;
		var dVal;
		var eVal;
		var fVal;
		if(auswerA !=''){
			aVal = parseInt(sumA);
			myData = new Array([auswerA, aVal]);
			colors = ['#8A2BE2'];
			wightZ = 90;
		}
		if(auswerB !=''){
			bVal = parseInt(sumB);
			myData = new Array([auswerA, aVal], [auswerB, bVal]);
			colors = ['#8A2BE2', '#CD4F39'];
			wightZ = 80;
		}
		if(auswerC !=''){
			cVal = parseInt(sumC);
			myData = new Array([auswerA, aVal], [auswerB, bVal], [auswerC, cVal]);
			colors = ['#8A2BE2', '#CD4F39', '#45818e'];
			wightZ = 70;
		}
		if(auswerD !=''){
			dVal = parseInt(sumD);
			myData = new Array([auswerA, aVal], [auswerB, bVal], [auswerC, cVal], [auswerD, dVal]);
			colors = ['#8A2BE2', '#CD4F39', '#45818e', '#CD69C9'];
			wightZ = 60;
		}
		if(auswerE !=''){
			eVal = parseInt(sumE);
			myData = new Array([auswerA, aVal], [auswerB, bVal], [auswerC, cVal], [auswerD, dVal],[auswerE,eVal]);
			colors = ['#8A2BE2', '#CD4F39', '#45818e', '#CD69C9', '#8B4726'];
			wightZ = 50;
		}
		if(auswerF !=''){
			fVal = parseInt(sumF);
			myData = new Array([auswerA, aVal], [auswerB, bVal], [auswerC, cVal], [auswerD, dVal],[auswerE,eVal],[auswerF,fVal]);
			colors = ['#8A2BE2', '#CD4F39', '#45818e', '#CD69C9', '#8B4726','#274e13'];
			wightZ = 50;
		}
		if(aVal>=20 && aVal%2!=0){
			aVal = 3;
		}
		//alert(aVal);
		if(auswerA !=''){
			myChart.setDataArray(myData);
			myChart.colorizeBars(colors);
			myChart.setTitle('投票结果统计');
			myChart.setTitleFontSize(35);//title字体大小
			myChart.setTitleColor('#FFFFFF');
			myChart.setAxisNameX(' ');//X轴名称
			myChart.setAxisNameY(' ');//Y轴名称
			myChart.setAxisValuesNumberY(9);
			myChart.setAxisColor('#FFFFFF');//轴线颜色
			myChart.setAxisNameFontSize(40);//轴字体大小
			myChart.setAxisNameColor('#AF0202');//轴名字字体颜色
			myChart.setAxisValuesColor('#3c78d8');//值颜色
			myChart.setAxisValuesFontSize(30);//设置两轴上值的字体大小，对饼图无效。
			myChart.setBarValuesColor('#AF0202');//选择值颜色
			myChart.setBarValuesFontSize(40);//设置柱状图矩形值的字体大小，默认8.
			myChart.setAxisPaddingTop(70);//轴距顶
			myChart.setAxisPaddingRight(140);//轴距右
			myChart.setAxisPaddingLeft(180);//轴距左
			myChart.setAxisPaddingBottom(90);//轴距低
			myChart.setTextPaddingBottom(10);
			myChart.setTextPaddingLeft(90);
			myChart.setBarBorderWidth(2);//柱边框宽度
			myChart.setBarBorderColor('#d9d9d9');//柱边框颜色
			myChart.setBarSpacingRatio(wightZ);//柱宽度
			myChart.setGrid(true);
			myChart.setGridColor('#FFFFFF');
			myChart.setIntervalEndY(80);
			myChart.setSize(wideVal, heightVal);
			myChart.draw();
		}
	}
} 
catch (e) 
{ }
