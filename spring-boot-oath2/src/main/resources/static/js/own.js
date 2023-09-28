var margin={top:20,right:50,bottom:30,left:50},width=960-margin.left-margin.right,height=500-margin.top-margin.bottom;

var parseData=d3.timeParse("%d-%b-%y");
var x=techan.scale.financetime().range([0,width]);

var y=d3.scaleLinear().range([height,0]);

var candlestick=techan.plot.candlestick().xScale(x).yScale(y);

var xAxis=d3.axisBottom(x);

var xTopAxis=d3.axisTop(x);

var yAxis=d3.axisLeft(y);

var yRightAxis=d3.axisRight(y);

var ohlcAnnotation=techan.plot.axisannotation().axis(yAxis).orient('left').format(d3.format(',.2f'));

var ohlcRightAnnotation=techan.plot.axisannotation().axis(yRightAxis).orient('right').translate([width,0]);

var timeAnnotation=techan.plot.axisannotation().axis(xAxis).orient('bottom').format(d3.timeFormat('%Y-%m-%d')).width(65).translate([0,height]);

var timeTopAnnotation=techan.plot.axisannotation().axis(xTopAxis).orient('top');

var crosshair=techan.plot.crosshair().xScale(x).yScale(y).xAnnotation([timeAnnotation,timeTopAnnotation]).yAnnotation([ohlcAnnotation,ohlcRightAnnotation]).on("enter",enter).on("out",out).on("move",move);

var svg=d3.select("#salse-revenue");

var coordsText=svg.append('text').style("text-anchor","end").attr("class","coords").attr("x",width-5).attr("y",15);

let url = 'http://localhost:9999/findhist/getStock';  // 替換為您的 API 網址
function getCsrfToken() {
  const cookies = document.cookie.split('; ');
  for (const cookie of cookies) {
      const [name, value] = cookie.split('=');
      if (name === 'XSRF-TOKEN') {
          return decodeURIComponent(value);
      }
  }
  return null;
}

d3.json(`${url}`)
  .then(response => {
  // 在這裡處理回應
  console.log("Success");
  var myDiv = document.getElementById('salse-revenue');
  // 替換 HTML 內容
  // myDiv.innerHTML = response[0]['transactVolume'];
  var stockDatas = response;
  var accessor=candlestick.accessor();
  data=stockDatas.slice(0,200).map(function(d){
    return{
      data: parseData(d.Date),
      open: +d.Open,
      high: +d.High,
      low: +d.Low,
      close: +d.close,
      volumn: +d.volumn
    };
  }).sort(function(a,b){return d3.ascending(accessor.d(a),accessor.d(b));});

  x.domain(data.map(accessor.d));

  y.domain(techan.scale.plot.ohlc(data,accessor).domain());

  svg.append("g").datum(data).attr("class","candlestick").call(candlestick);

  svg.append("g").attr("class","x axis").call(xTopAxis);

  svg.append("g").attr("class","x axis").attr("transform","translate(0,"+height+")").call(xAxis);

  svg.append("g").attr("class","y axis").call(yAxis);

  svg.append("g").attr("class","y axis").attr("tranasform","translate("+width+",0)").call(yRightAxis);

  svg.append("g").attr("class","y annotation left").datum([{value:74},{value: 67.5},{value: 58},{value:40}]).call(ohlcAnnotation);

  svg.append("g").attr("class","x annotation bottom").datum([{value: x.domain()[30]}]).call(timeAnnotation);

  svg.append("g").attr("class","y annotatioin right").datum([{value: 61},{value: 52}]).call(ohlcRightAnnotation);

  svg.append("g").attr("class","x annotation top").datum([{value: x.domain()[80]}]).call(timeTopAnnotation);

  svg.append("g").attr("class","crosshair").datum({x:x.domain()[80],y:67.5}).call(crosshair).each(function(d){move(d);});

  svg.append("text").attr("x",5).attr("y",15).text("Facebook,Inc. (FB)");

  console.log(">>> "+svg);
  })
  .catch(error => {
    // 在這裡處理錯誤
    console.error(error);
  });


function enter(){
  coordsText.style("display","inline");
}

function out(){
  coordsText.style("display","none");
}

function move(coords){
  coordsText.text(
    timeAnnotation.format()(coords.x)+","+ohlcRightAnnotation.format((coords.y)));
}



// let url = 'http://192.168.126.134/findhist/getStock';  // 替換為您的 API 網址

// let stockDatas;

//

// var showData = document.getElementById("showData");
// console.log(showData);

// function clearEle() {
//   var svg = d3.select('svg');
//   svg.selectAll('.x-axis').remove();
//   svg.selectAll('.y-axis').remove();
// }



// showData.addEventListener("click", function () {
//   clearEle();
//   const width = 800;
//   const height = 500;
//   const svg = d3.select('svg').attr('width', width).attr('height', height);
//   // const svg=d3.select('#svg');
//   const margin = { top: 10, right: 30, bottom: 60, left: 70 };
//   const innerWidth = width - margin.left - margin.right;
//   const innerHeight = height - margin.top - margin.bottom;

//   // console.log(stockDatas);
//   const dateExtent = d3.extent(stockDatas, d => convertRocToDate(d.date));

//   const xScale = d3.scaleTime()
//     .domain(dateExtent)
//     .range([0, innerWidth]);


//   const dateFormatter = d3.timeFormat("%Y-%m-%d");


//   const xAxis = d3.axisBottom(xScale);

//   xAxis.ticks(200).tickFormat(dateFormatter).tickPadding(10);


//   const g = svg.append('g').attr('id', 'maingroup')
//     .attr('transform', `translate(${margin.left},${margin.top})`);

//   g.append('g').attr('transform', `translate(0,${innerHeight})`).attr("class", "x-axis").call(xAxis);

//   // 設定圖型
//   const yScale = d3.scaleLinear()
//     .domain([d3.min(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, ""))), d3.max(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, "")))])
//     .range([innerHeight, 0]);


//   const yAxis = d3.axisLeft(yScale);
//   // 顯示軸線
//   g.append('g').attr("class", "y-axis").call(yAxis);
//   yAxis.ticks(stockDatas.length);

//   // 綁定圖型
//   const yRect = d3.scaleLinear()
//     .domain([d3.min(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, ""))), d3.max(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, "")))])
//     .range([innerHeight, 0]);

//   // 顯示圖型
//   const rects = g.selectAll('rect').data(stockDatas).enter().append('rect')
//     // .attr('x',(d,i)=>{return (i+1)*(innerWidth/stockDatas.length)})
//     .attr('x', (d, i) => innerWidth / (stockDatas.length * 2) + xScale(convertRocToDate(d.date)))
//     .attr('width', 4)
//     .attr('height', (d, i) => yRect(parseInt(d.transactVolume.replace(/,/g, ""))))
//     .attr('y', (d, i) => innerHeight - yRect(parseInt(d.transactVolume.replace(/,/g, ""))))
//     .attr('fill', 'steelblue')
//     .on('mouseover', function () {
//       d3.select(this).attr('opacity', '0,5').attr('stroke', 'green').attr('fill', 'green');
//     })
//     .on('mouseout', function () {
//       d3.select(this).attr('opacity', '0,5').attr('stroke', 'steelblue').attr('fill', 'steelblue');
//     });

//   // 將日期轉角度
//   g.selectAll('.x-axis .tick text')
//     .attr('transform', 'rotate(-90)')
//     .attr('y', innerWidth / (stockDatas.length * 2))
//     .style("text-anchor", "end");

// }, false);

// function convertRocToDate(rocDateStr) {
//   const [rocYear, month, day] = rocDateStr.split('-');
//   const year = parseInt(rocYear) + 1911;
//   const date = new Date(year, month - 1, day);
//   console.log(rocDateStr + "=" + date);

//   return date;
// }


build_table();
function build_table(){
  var trObj=$("tr").text("it is a table");
  $("#tb").append($('<tr>')
  .append($('<td>')
      .append($('<img>')
          .attr('src', 'img.png')
          .text('Image cell')
      )
  )
);
}


var jsonDatas='[{"stockCode":"2330","date":"112-01-03","transactVolume":"15,311,364","totalPrice":"6,871,973,708","startPrice":"446.00","highestPrice":"453.50","lowestPrice":"443.00","endPrice":"453.00","upAndDown":"+4.50","sharesTradedNum":"22,581"},{"stockCode":"2330","date":"112-01-04","transactVolume":"20,626,874","totalPrice":"9,310,050,329","startPrice":"449.50","highestPrice":"455.00","lowestPrice":"448.50","endPrice":"449.50","upAndDown":"-3.50","sharesTradedNum":"18,233"},{"stockCode":"2330","date":"112-01-05","transactVolume":"23,972,099","totalPrice":"10,972,616,269","startPrice":"459.00","highestPrice":"459.50","lowestPrice":"455.00","endPrice":"458.50","upAndDown":"+9.00","sharesTradedNum":"20,752"},{"stockCode":"2330","date":"112-01-06","transactVolume":"21,313,593","totalPrice":"9,745,142,549","startPrice":"455.00","highestPrice":"459.50","lowestPrice":"455.00","endPrice":"458.50","upAndDown":" 0.00","sharesTradedNum":"16,635"},{"stockCode":"2330","date":"112-01-09","transactVolume":"49,186,355","totalPrice":"23,352,375,299","startPrice":"468.00","highestPrice":"481.00","lowestPrice":"467.50","endPrice":"481.00","upAndDown":"+22.50","sharesTradedNum":"57,305"},{"stockCode":"2330","date":"112-01-10","transactVolume":"34,785,370","totalPrice":"16,867,391,241","startPrice":"486.00","highestPrice":"487.00","lowestPrice":"483.00","endPrice":"486.00","upAndDown":"+5.00","sharesTradedNum":"36,975"},{"stockCode":"2330","date":"112-01-11","transactVolume":"21,749,124","totalPrice":"10,531,314,878","startPrice":"487.00","highestPrice":"488.00","lowestPrice":"482.00","endPrice":"484.50","upAndDown":"-1.50","sharesTradedNum":"25,457"},{"stockCode":"2330","date":"112-01-12","transactVolume":"22,416,984","totalPrice":"10,901,640,739","startPrice":"487.50","highestPrice":"488.00","lowestPrice":"484.00","endPrice":"486.50","upAndDown":"+2.00","sharesTradedNum":"25,780"},{"stockCode":"2330","date":"112-01-13","transactVolume":"81,848,755","totalPrice":"41,113,175,651","startPrice":"507.00","highestPrice":"509.00","lowestPrice":"499.00","endPrice":"500.00","upAndDown":"+13.50","sharesTradedNum":"93,121"},{"stockCode":"2330","date":"112-01-16","transactVolume":"38,781,925","totalPrice":"19,573,968,332","startPrice":"506.00","highestPrice":"508.00","lowestPrice":"503.00","endPrice":"505.00","upAndDown":"+5.00","sharesTradedNum":"48,051"},{"stockCode":"2330","date":"112-01-17","transactVolume":"29,443,522","totalPrice":"14,784,661,794","startPrice":"504.00","highestPrice":"504.00","lowestPrice":"499.50","endPrice":"503.00","upAndDown":"-2.00","sharesTradedNum":"27,930"},{"stockCode":"2330","date":"112-01-30","transactVolume":"148,413,161","totalPrice":"80,057,158,264","startPrice":"542.00","highestPrice":"543.00","lowestPrice":"534.00","endPrice":"543.00","upAndDown":"+40.00","sharesTradedNum":"153,125"},{"stockCode":"2330","date":"112-01-31","transactVolume":"79,212,272","totalPrice":"41,736,937,234","startPrice":"537.00","highestPrice":"538.00","lowestPrice":"521.00","endPrice":"522.00","upAndDown":"-21.00","sharesTradedNum":"74,279"},{"stockCode":"2330","date":"112-02-01","transactVolume":"42,539,626","totalPrice":"22,450,948,467","startPrice":"532.00","highestPrice":"533.00","lowestPrice":"522.00","endPrice":"530.00","upAndDown":"+8.00","sharesTradedNum":"31,487"},{"stockCode":"2330","date":"112-02-02","transactVolume":"46,523,592","totalPrice":"25,091,483,557","startPrice":"538.00","highestPrice":"542.00","lowestPrice":"537.00","endPrice":"540.00","upAndDown":"+10.00","sharesTradedNum":"45,495"},{"stockCode":"2330","date":"112-02-03","transactVolume":"34,156,234","totalPrice":"18,439,613,447","startPrice":"540.00","highestPrice":"542.00","lowestPrice":"536.00","endPrice":"542.00","upAndDown":"+2.00","sharesTradedNum":"39,674"},{"stockCode":"2330","date":"112-02-06","transactVolume":"32,208,918","totalPrice":"17,041,821,849","startPrice":"532.00","highestPrice":"533.00","lowestPrice":"526.00","endPrice":"526.00","upAndDown":"-16.00","sharesTradedNum":"46,279"},{"stockCode":"2330","date":"112-02-07","transactVolume":"24,362,116","totalPrice":"12,767,910,080","startPrice":"524.00","highestPrice":"528.00","lowestPrice":"522.00","endPrice":"523.00","upAndDown":"-3.00","sharesTradedNum":"28,519"},{"stockCode":"2330","date":"112-02-08","transactVolume":"37,633,169","totalPrice":"20,247,927,642","startPrice":"538.00","highestPrice":"540.00","lowestPrice":"534.00","endPrice":"540.00","upAndDown":"+17.00","sharesTradedNum":"43,495"},{"stockCode":"2330","date":"112-02-09","transactVolume":"22,311,345","totalPrice":"12,026,317,167","startPrice":"540.00","highestPrice":"540.00","lowestPrice":"536.00","endPrice":"540.00","upAndDown":" 0.00","sharesTradedNum":"19,698"},{"stockCode":"2330","date":"112-02-10","transactVolume":"30,445,088","totalPrice":"16,545,221,176","startPrice":"544.00","highestPrice":"546.00","lowestPrice":"541.00","endPrice":"545.00","upAndDown":"+5.00","sharesTradedNum":"40,984"},{"stockCode":"2330","date":"112-02-13","transactVolume":"23,903,151","totalPrice":"12,921,896,752","startPrice":"544.00","highestPrice":"544.00","lowestPrice":"538.00","endPrice":"541.00","upAndDown":"-4.00","sharesTradedNum":"22,431"},{"stockCode":"2330","date":"112-02-14","transactVolume":"23,855,974","totalPrice":"12,990,943,260","startPrice":"544.00","highestPrice":"546.00","lowestPrice":"543.00","endPrice":"545.00","upAndDown":"+4.00","sharesTradedNum":"27,983"},{"stockCode":"2330","date":"112-02-15","transactVolume":"66,068,713","totalPrice":"34,751,111,404","startPrice":"526.00","highestPrice":"532.00","lowestPrice":"522.00","endPrice":"525.00","upAndDown":"-20.00","sharesTradedNum":"103,433"},{"stockCode":"2330","date":"112-02-16","transactVolume":"33,509,968","totalPrice":"17,692,691,883","startPrice":"528.00","highestPrice":"531.00","lowestPrice":"524.00","endPrice":"528.00","upAndDown":"+3.00","sharesTradedNum":"27,033"},{"stockCode":"2330","date":"112-02-17","transactVolume":"29,673,053","totalPrice":"15,385,668,299","startPrice":"524.00","highestPrice":"524.00","lowestPrice":"516.00","endPrice":"518.00","upAndDown":"-10.00","sharesTradedNum":"55,965"},{"stockCode":"2330","date":"112-02-20","transactVolume":"21,181,235","totalPrice":"10,928,087,780","startPrice":"514.00","highestPrice":"519.00","lowestPrice":"511.00","endPrice":"517.00","upAndDown":"-1.00","sharesTradedNum":"25,898"},{"stockCode":"2330","date":"112-02-21","transactVolume":"15,097,509","totalPrice":"7,771,141,142","startPrice":"515.00","highestPrice":"517.00","lowestPrice":"513.00","endPrice":"516.00","upAndDown":"-1.00","sharesTradedNum":"27,200"},{"stockCode":"2330","date":"112-02-22","transactVolume":"26,578,867","totalPrice":"13,474,662,693","startPrice":"506.00","highestPrice":"509.00","lowestPrice":"505.00","endPrice":"507.00","upAndDown":"-9.00","sharesTradedNum":"47,843"},{"stockCode":"2330","date":"112-02-23","transactVolume":"26,801,677","totalPrice":"13,846,782,345","startPrice":"512.00","highestPrice":"521.00","lowestPrice":"510.00","endPrice":"518.00","upAndDown":"+11.00","sharesTradedNum":"26,552"},{"stockCode":"2330","date":"112-02-24","transactVolume":"48,372,303","totalPrice":"24,932,422,713","startPrice":"524.00","highestPrice":"525.00","lowestPrice":"511.00","endPrice":"511.00","upAndDown":"-7.00","sharesTradedNum":"29,612"},{"stockCode":"2330","date":"112-03-01","transactVolume":"45,971,058","totalPrice":"23,624,972,884","startPrice":"504.00","highestPrice":"522.00","lowestPrice":"504.00","endPrice":"522.00","upAndDown":"+11.00","sharesTradedNum":"31,738"},{"stockCode":"2330","date":"112-03-02","transactVolume":"18,017,085","totalPrice":"9,344,352,321","startPrice":"519.00","highestPrice":"520.00","lowestPrice":"515.00","endPrice":"519.00","upAndDown":"-3.00","sharesTradedNum":"14,320"},{"stockCode":"2330","date":"112-03-03","transactVolume":"24,117,189","totalPrice":"12,510,608,773","startPrice":"524.00","highestPrice":"525.00","lowestPrice":"516.00","endPrice":"516.00","upAndDown":"-3.00","sharesTradedNum":"22,160"},{"stockCode":"2330","date":"112-03-06","transactVolume":"24,741,125","totalPrice":"12,883,854,256","startPrice":"520.00","highestPrice":"524.00","lowestPrice":"517.00","endPrice":"521.00","upAndDown":"+5.00","sharesTradedNum":"20,678"},{"stockCode":"2330","date":"112-03-07","transactVolume":"27,650,283","totalPrice":"14,465,320,023","startPrice":"521.00","highestPrice":"526.00","lowestPrice":"519.00","endPrice":"524.00","upAndDown":"+3.00","sharesTradedNum":"20,595"},{"stockCode":"2330","date":"112-03-08","transactVolume":"27,077,587","totalPrice":"14,091,713,576","startPrice":"521.00","highestPrice":"523.00","lowestPrice":"518.00","endPrice":"521.00","upAndDown":"-3.00","sharesTradedNum":"20,504"},{"stockCode":"2330","date":"112-03-09","transactVolume":"25,639,391","totalPrice":"13,430,582,677","startPrice":"525.00","highestPrice":"526.00","lowestPrice":"522.00","endPrice":"522.00","upAndDown":"+1.00","sharesTradedNum":"16,497"},{"stockCode":"2330","date":"112-03-10","transactVolume":"25,572,403","totalPrice":"13,122,265,260","startPrice":"515.00","highestPrice":"516.00","lowestPrice":"511.00","endPrice":"513.00","upAndDown":"-9.00","sharesTradedNum":"46,216"},{"stockCode":"2330","date":"112-03-13","transactVolume":"22,568,740","totalPrice":"11,608,973,891","startPrice":"513.00","highestPrice":"518.00","lowestPrice":"509.00","endPrice":"516.00","upAndDown":"+3.00","sharesTradedNum":"24,150"},{"stockCode":"2330","date":"112-03-14","transactVolume":"26,599,476","totalPrice":"13,602,260,966","startPrice":"511.00","highestPrice":"514.00","lowestPrice":"510.00","endPrice":"510.00","upAndDown":"-6.00","sharesTradedNum":"24,848"},{"stockCode":"2330","date":"112-03-15","transactVolume":"24,080,361","totalPrice":"12,350,942,139","startPrice":"515.00","highestPrice":"516.00","lowestPrice":"510.00","endPrice":"511.00","upAndDown":"+1.00","sharesTradedNum":"21,075"},{"stockCode":"2330","date":"112-03-16","transactVolume":"23,325,664","totalPrice":"11,809,911,840","startPrice":"505.00","highestPrice":"510.00","lowestPrice":"504.00","endPrice":"505.00","upAndDown":"X0.00","sharesTradedNum":"33,493"},{"stockCode":"2330","date":"112-03-17","transactVolume":"35,872,881","totalPrice":"18,538,033,584","startPrice":"516.00","highestPrice":"518.00","lowestPrice":"513.00","endPrice":"518.00","upAndDown":"+13.00","sharesTradedNum":"21,217"},{"stockCode":"2330","date":"112-03-20","transactVolume":"12,752,221","totalPrice":"6,542,889,562","startPrice":"517.00","highestPrice":"518.00","lowestPrice":"510.00","endPrice":"512.00","upAndDown":"-6.00","sharesTradedNum":"17,709"},{"stockCode":"2330","date":"112-03-21","transactVolume":"18,519,120","totalPrice":"9,534,730,452","startPrice":"515.00","highestPrice":"517.00","lowestPrice":"512.00","endPrice":"517.00","upAndDown":"+5.00","sharesTradedNum":"14,094"},{"stockCode":"2330","date":"112-03-22","transactVolume":"45,681,056","totalPrice":"24,168,561,556","startPrice":"524.00","highestPrice":"533.00","lowestPrice":"524.00","endPrice":"533.00","upAndDown":"+16.00","sharesTradedNum":"56,483"},{"stockCode":"2330","date":"112-03-23","transactVolume":"28,614,856","totalPrice":"15,307,492,846","startPrice":"528.00","highestPrice":"539.00","lowestPrice":"528.00","endPrice":"538.00","upAndDown":"+5.00","sharesTradedNum":"35,313"},{"stockCode":"2330","date":"112-03-24","transactVolume":"22,963,771","totalPrice":"12,348,311,773","startPrice":"536.00","highestPrice":"539.00","lowestPrice":"535.00","endPrice":"539.00","upAndDown":"+1.00","sharesTradedNum":"24,762"},{"stockCode":"2330","date":"112-03-27","transactVolume":"17,170,557","totalPrice":"9,148,085,033","startPrice":"533.00","highestPrice":"536.00","lowestPrice":"531.00","endPrice":"531.00","upAndDown":"-8.00","sharesTradedNum":"17,484"},{"stockCode":"2330","date":"112-03-28","transactVolume":"18,327,556","totalPrice":"9,629,323,504","startPrice":"525.00","highestPrice":"530.00","lowestPrice":"524.00","endPrice":"525.00","upAndDown":"-6.00","sharesTradedNum":"20,744"},{"stockCode":"2330","date":"112-03-29","transactVolume":"17,620,195","totalPrice":"9,329,137,251","startPrice":"533.00","highestPrice":"533.00","lowestPrice":"525.00","endPrice":"530.00","upAndDown":"+5.00","sharesTradedNum":"15,653"},{"stockCode":"2330","date":"112-03-30","transactVolume":"20,380,650","totalPrice":"10,894,446,851","startPrice":"536.00","highestPrice":"537.00","lowestPrice":"531.00","endPrice":"535.00","upAndDown":"+5.00","sharesTradedNum":"18,171"},{"stockCode":"2330","date":"112-03-31","transactVolume":"24,324,776","totalPrice":"12,997,575,231","startPrice":"538.00","highestPrice":"538.00","lowestPrice":"532.00","endPrice":"533.00","upAndDown":"-2.00","sharesTradedNum":"15,962"},{"stockCode":"2330","date":"112-04-06","transactVolume":"26,706,549","totalPrice":"14,130,223,516","startPrice":"530.00","highestPrice":"531.00","lowestPrice":"526.00","endPrice":"530.00","upAndDown":"-3.00","sharesTradedNum":"19,144"},{"stockCode":"2330","date":"112-04-07","transactVolume":"11,114,272","totalPrice":"5,903,807,797","startPrice":"535.00","highestPrice":"535.00","lowestPrice":"529.00","endPrice":"531.00","upAndDown":"+1.00","sharesTradedNum":"10,285"},{"stockCode":"2330","date":"112-04-10","transactVolume":"8,203,168","totalPrice":"4,350,907,176","startPrice":"533.00","highestPrice":"533.00","lowestPrice":"528.00","endPrice":"529.00","upAndDown":"-2.00","sharesTradedNum":"11,698"},{"stockCode":"2330","date":"112-04-11","transactVolume":"23,087,326","totalPrice":"12,101,146,147","startPrice":"522.00","highestPrice":"527.00","lowestPrice":"522.00","endPrice":"524.00","upAndDown":"-5.00","sharesTradedNum":"24,432"},{"stockCode":"2330","date":"112-04-12","transactVolume":"24,772,925","totalPrice":"12,870,788,209","startPrice":"523.00","highestPrice":"524.00","lowestPrice":"517.00","endPrice":"520.00","upAndDown":"-4.00","sharesTradedNum":"37,158"},{"stockCode":"2330","date":"112-04-13","transactVolume":"27,501,436","totalPrice":"14,121,978,133","startPrice":"515.00","highestPrice":"517.00","lowestPrice":"510.00","endPrice":"510.00","upAndDown":"-10.00","sharesTradedNum":"64,834"},{"stockCode":"2330","date":"112-04-14","transactVolume":"21,174,653","totalPrice":"10,936,462,759","startPrice":"516.00","highestPrice":"520.00","lowestPrice":"513.00","endPrice":"516.00","upAndDown":"+6.00","sharesTradedNum":"19,962"},{"stockCode":"2330","date":"112-04-17","transactVolume":"17,895,837","totalPrice":"9,264,789,087","startPrice":"519.00","highestPrice":"520.00","lowestPrice":"514.00","endPrice":"520.00","upAndDown":"+4.00","sharesTradedNum":"17,444"},{"stockCode":"2330","date":"112-04-18","transactVolume":"15,687,059","totalPrice":"8,097,905,529","startPrice":"518.00","highestPrice":"520.00","lowestPrice":"514.00","endPrice":"515.00","upAndDown":"-5.00","sharesTradedNum":"17,087"},{"stockCode":"2330","date":"112-04-19","transactVolume":"23,209,923","totalPrice":"11,881,404,238","startPrice":"517.00","highestPrice":"518.00","lowestPrice":"509.00","endPrice":"510.00","upAndDown":"-5.00","sharesTradedNum":"43,281"},{"stockCode":"2330","date":"112-04-20","transactVolume":"21,002,685","totalPrice":"10,780,268,259","startPrice":"512.00","highestPrice":"516.00","lowestPrice":"510.00","endPrice":"513.00","upAndDown":"+3.00","sharesTradedNum":"16,627"},{"stockCode":"2330","date":"112-04-21","transactVolume":"25,440,179","totalPrice":"13,103,618,008","startPrice":"520.00","highestPrice":"520.00","lowestPrice":"510.00","endPrice":"511.00","upAndDown":"-2.00","sharesTradedNum":"22,738"},{"stockCode":"2330","date":"112-04-24","transactVolume":"17,579,661","totalPrice":"8,903,570,323","startPrice":"505.00","highestPrice":"510.00","lowestPrice":"504.00","endPrice":"507.00","upAndDown":"-4.00","sharesTradedNum":"31,635"},{"stockCode":"2330","date":"112-04-25","transactVolume":"40,916,105","totalPrice":"20,495,640,143","startPrice":"504.00","highestPrice":"505.00","lowestPrice":"498.00","endPrice":"498.00","upAndDown":"-9.00","sharesTradedNum":"92,009"},{"stockCode":"2330","date":"112-04-26","transactVolume":"33,174,539","totalPrice":"16,353,670,134","startPrice":"498.00","highestPrice":"498.50","lowestPrice":"491.00","endPrice":"491.50","upAndDown":"-6.50","sharesTradedNum":"58,580"},{"stockCode":"2330","date":"112-04-27","transactVolume":"34,149,043","totalPrice":"16,816,693,302","startPrice":"491.00","highestPrice":"495.00","lowestPrice":"489.00","endPrice":"493.50","upAndDown":"+2.00","sharesTradedNum":"47,699"},{"stockCode":"2330","date":"112-04-28","transactVolume":"38,812,949","totalPrice":"19,433,015,577","startPrice":"498.50","highestPrice":"502.00","lowestPrice":"498.00","endPrice":"502.00","upAndDown":"+8.50","sharesTradedNum":"23,746"},{"stockCode":"2330","date":"112-05-02","transactVolume":"17,142,380","totalPrice":"8,572,554,842","startPrice":"500.00","highestPrice":"502.00","lowestPrice":"496.50","endPrice":"501.00","upAndDown":"-1.00","sharesTradedNum":"18,247"},{"stockCode":"2330","date":"112-05-03","transactVolume":"12,694,698","totalPrice":"6,305,914,905","startPrice":"496.00","highestPrice":"498.00","lowestPrice":"495.00","endPrice":"496.00","upAndDown":"-5.00","sharesTradedNum":"25,658"},{"stockCode":"2330","date":"112-05-04","transactVolume":"13,699,933","totalPrice":"6,818,128,036","startPrice":"497.00","highestPrice":"499.50","lowestPrice":"496.00","endPrice":"498.00","upAndDown":"+2.00","sharesTradedNum":"14,801"},{"stockCode":"2330","date":"112-05-05","transactVolume":"7,898,012","totalPrice":"3,949,232,374","startPrice":"500.00","highestPrice":"502.00","lowestPrice":"498.50","endPrice":"500.00","upAndDown":"+2.00","sharesTradedNum":"10,944"},{"stockCode":"2330","date":"112-05-08","transactVolume":"11,737,287","totalPrice":"5,932,968,954","startPrice":"509.00","highestPrice":"509.00","lowestPrice":"502.00","endPrice":"504.00","upAndDown":"+4.00","sharesTradedNum":"13,709"},{"stockCode":"2330","date":"112-05-09","transactVolume":"18,762,778","totalPrice":"9,530,653,802","startPrice":"507.00","highestPrice":"510.00","lowestPrice":"505.00","endPrice":"510.00","upAndDown":"+6.00","sharesTradedNum":"17,452"},{"stockCode":"2330","date":"112-05-10","transactVolume":"19,385,820","totalPrice":"9,753,130,414","startPrice":"508.00","highestPrice":"508.00","lowestPrice":"500.00","endPrice":"503.00","upAndDown":"-7.00","sharesTradedNum":"26,320"},{"stockCode":"2330","date":"112-05-11","transactVolume":"13,775,130","totalPrice":"6,894,746,011","startPrice":"506.00","highestPrice":"506.00","lowestPrice":"498.50","endPrice":"499.00","upAndDown":"-4.00","sharesTradedNum":"20,237"},{"stockCode":"2330","date":"112-05-12","transactVolume":"20,746,928","totalPrice":"10,313,384,387","startPrice":"496.00","highestPrice":"500.00","lowestPrice":"495.00","endPrice":"496.00","upAndDown":"-3.00","sharesTradedNum":"27,989"},{"stockCode":"2330","date":"112-05-15","transactVolume":"15,548,031","totalPrice":"7,721,867,966","startPrice":"497.00","highestPrice":"499.50","lowestPrice":"494.50","endPrice":"495.50","upAndDown":"-0.50","sharesTradedNum":"16,896"},{"stockCode":"2330","date":"112-05-16","transactVolume":"24,052,785","totalPrice":"12,131,536,763","startPrice":"503.00","highestPrice":"508.00","lowestPrice":"500.00","endPrice":"505.00","upAndDown":"+9.50","sharesTradedNum":"22,355"},{"stockCode":"2330","date":"112-05-17","transactVolume":"44,352,410","totalPrice":"22,870,340,267","startPrice":"508.00","highestPrice":"521.00","lowestPrice":"506.00","endPrice":"519.00","upAndDown":"+14.00","sharesTradedNum":"52,663"},{"stockCode":"2330","date":"112-05-18","transactVolume":"46,107,848","totalPrice":"24,437,734,052","startPrice":"533.00","highestPrice":"536.00","lowestPrice":"526.00","endPrice":"530.00","upAndDown":"+11.00","sharesTradedNum":"50,359"},{"stockCode":"2330","date":"112-05-19","transactVolume":"34,742,592","totalPrice":"18,476,363,730","startPrice":"535.00","highestPrice":"535.00","lowestPrice":"529.00","endPrice":"532.00","upAndDown":"+2.00","sharesTradedNum":"39,104"},{"stockCode":"2330","date":"112-05-22","transactVolume":"19,037,508","totalPrice":"10,100,912,410","startPrice":"532.00","highestPrice":"533.00","lowestPrice":"529.00","endPrice":"531.00","upAndDown":"-1.00","sharesTradedNum":"18,796"},{"stockCode":"2330","date":"112-05-23","transactVolume":"20,126,817","totalPrice":"10,638,352,410","startPrice":"530.00","highestPrice":"531.00","lowestPrice":"525.00","endPrice":"530.00","upAndDown":"-1.00","sharesTradedNum":"15,826"},{"stockCode":"2330","date":"112-05-24","transactVolume":"22,621,221","totalPrice":"11,888,325,332","startPrice":"527.00","highestPrice":"528.00","lowestPrice":"524.00","endPrice":"525.00","upAndDown":"-5.00","sharesTradedNum":"16,339"},{"stockCode":"2330","date":"112-05-25","transactVolume":"68,360,924","totalPrice":"36,976,525,917","startPrice":"542.00","highestPrice":"545.00","lowestPrice":"537.00","endPrice":"543.00","upAndDown":"+18.00","sharesTradedNum":"80,930"},{"stockCode":"2330","date":"112-05-26","transactVolume":"104,924,623","totalPrice":"59,322,536,825","startPrice":"568.00","highestPrice":"568.00","lowestPrice":"563.00","endPrice":"566.00","upAndDown":"+23.00","sharesTradedNum":"131,612"},{"stockCode":"2330","date":"112-05-29","transactVolume":"58,813,792","totalPrice":"33,454,887,785","startPrice":"574.00","highestPrice":"574.00","lowestPrice":"564.00","endPrice":"568.00","upAndDown":"+2.00","sharesTradedNum":"52,250"},{"stockCode":"2330","date":"112-05-30","transactVolume":"47,490,594","totalPrice":"26,861,389,177","startPrice":"566.00","highestPrice":"568.00","lowestPrice":"563.00","endPrice":"566.00","upAndDown":"-2.00","sharesTradedNum":"32,956"},{"stockCode":"2330","date":"112-05-31","transactVolume":"90,675,529","totalPrice":"50,573,324,515","startPrice":"560.00","highestPrice":"562.00","lowestPrice":"551.00","endPrice":"558.00","upAndDown":"-8.00","sharesTradedNum":"39,679"},{"stockCode":"2330","date":"112-06-01","transactVolume":"25,257,673","totalPrice":"13,920,836,412","startPrice":"550.00","highestPrice":"554.00","lowestPrice":"550.00","endPrice":"551.00","upAndDown":"-7.00","sharesTradedNum":"25,441"},{"stockCode":"2330","date":"112-06-02","transactVolume":"34,705,102","totalPrice":"19,460,144,774","startPrice":"559.00","highestPrice":"564.00","lowestPrice":"557.00","endPrice":"562.00","upAndDown":"+11.00","sharesTradedNum":"30,644"},{"stockCode":"2330","date":"112-06-05","transactVolume":"17,483,804","totalPrice":"9,730,882,750","startPrice":"560.00","highestPrice":"560.00","lowestPrice":"555.00","endPrice":"555.00","upAndDown":"-7.00","sharesTradedNum":"20,211"},{"stockCode":"2330","date":"112-06-06","transactVolume":"21,562,088","totalPrice":"12,043,550,635","startPrice":"554.00","highestPrice":"562.00","lowestPrice":"553.00","endPrice":"560.00","upAndDown":"+5.00","sharesTradedNum":"15,551"},{"stockCode":"2330","date":"112-06-07","transactVolume":"29,091,913","totalPrice":"16,449,319,451","startPrice":"561.00","highestPrice":"568.00","lowestPrice":"560.00","endPrice":"568.00","upAndDown":"+8.00","sharesTradedNum":"29,548"},{"stockCode":"2330","date":"112-06-08","transactVolume":"25,250,687","totalPrice":"14,190,442,437","startPrice":"562.00","highestPrice":"568.00","lowestPrice":"555.00","endPrice":"559.00","upAndDown":"-9.00","sharesTradedNum":"27,238"},{"stockCode":"2330","date":"112-06-09","transactVolume":"19,776,199","totalPrice":"11,160,654,540","startPrice":"561.00","highestPrice":"566.00","lowestPrice":"561.00","endPrice":"565.00","upAndDown":"+6.00","sharesTradedNum":"16,713"},{"stockCode":"2330","date":"112-06-12","transactVolume":"28,656,646","totalPrice":"16,431,644,333","startPrice":"574.00","highestPrice":"574.00","lowestPrice":"571.00","endPrice":"574.00","upAndDown":"+9.00","sharesTradedNum":"38,130"},{"stockCode":"2330","date":"112-06-13","transactVolume":"62,671,781","totalPrice":"37,077,358,748","startPrice":"593.00","highestPrice":"594.00","lowestPrice":"589.00","endPrice":"593.00","upAndDown":"+19.00","sharesTradedNum":"84,903"},{"stockCode":"2330","date":"112-06-14","transactVolume":"25,981,992","totalPrice":"15,311,616,372","startPrice":"590.00","highestPrice":"591.00","lowestPrice":"587.00","endPrice":"590.00","upAndDown":"-3.00","sharesTradedNum":"28,171"},{"stockCode":"2330","date":"112-06-15","transactVolume":"29,868,798","totalPrice":"17,618,169,127","startPrice":"590.00","highestPrice":"593.00","lowestPrice":"587.00","endPrice":"591.00","upAndDown":"X0.00","sharesTradedNum":"28,303"},{"stockCode":"2330","date":"112-06-16","transactVolume":"41,560,389","totalPrice":"24,417,862,365","startPrice":"590.00","highestPrice":"590.00","lowestPrice":"584.00","endPrice":"589.00","upAndDown":"-2.00","sharesTradedNum":"27,450"},{"stockCode":"2330","date":"112-06-19","transactVolume":"14,883,308","totalPrice":"8,682,282,040","startPrice":"583.00","highestPrice":"585.00","lowestPrice":"582.00","endPrice":"583.00","upAndDown":"-6.00","sharesTradedNum":"18,979"},{"stockCode":"2330","date":"112-06-20","transactVolume":"17,013,418","totalPrice":"9,902,274,410","startPrice":"579.00","highestPrice":"585.00","lowestPrice":"579.00","endPrice":"583.00","upAndDown":" 0.00","sharesTradedNum":"17,658"},{"stockCode":"2330","date":"112-06-21","transactVolume":"23,718,735","totalPrice":"13,782,152,333","startPrice":"582.00","highestPrice":"585.00","lowestPrice":"578.00","endPrice":"581.00","upAndDown":"-2.00","sharesTradedNum":"25,132"},{"stockCode":"2330","date":"112-06-26","transactVolume":"29,870,090","totalPrice":"17,192,208,130","startPrice":"576.00","highestPrice":"578.00","lowestPrice":"574.00","endPrice":"574.00","upAndDown":"-7.00","sharesTradedNum":"28,167"},{"stockCode":"2330","date":"112-06-27","transactVolume":"22,447,417","totalPrice":"12,847,067,878","startPrice":"570.00","highestPrice":"575.00","lowestPrice":"569.00","endPrice":"572.00","upAndDown":"-2.00","sharesTradedNum":"20,736"},{"stockCode":"2330","date":"112-06-28","transactVolume":"18,684,508","totalPrice":"10,734,873,692","startPrice":"579.00","highestPrice":"579.00","lowestPrice":"571.00","endPrice":"574.00","upAndDown":"+2.00","sharesTradedNum":"15,939"},{"stockCode":"2330","date":"112-06-29","transactVolume":"18,046,268","totalPrice":"10,372,330,946","startPrice":"578.00","highestPrice":"580.00","lowestPrice":"570.00","endPrice":"573.00","upAndDown":"-1.00","sharesTradedNum":"17,649"},{"stockCode":"2330","date":"112-06-30","transactVolume":"33,830,922","totalPrice":"19,401,712,736","startPrice":"570.00","highestPrice":"576.00","lowestPrice":"568.00","endPrice":"576.00","upAndDown":"+3.00","sharesTradedNum":"20,529"},{"stockCode":"2330","date":"112-07-03","transactVolume":"15,118,041","totalPrice":"8,743,824,984","startPrice":"578.00","highestPrice":"580.00","lowestPrice":"576.00","endPrice":"579.00","upAndDown":"+3.00","sharesTradedNum":"15,659"},{"stockCode":"2330","date":"112-07-04","transactVolume":"17,777,363","totalPrice":"10,361,905,547","startPrice":"585.00","highestPrice":"585.00","lowestPrice":"580.00","endPrice":"585.00","upAndDown":"+6.00","sharesTradedNum":"18,848"},{"stockCode":"2330","date":"112-07-05","transactVolume":"15,553,503","totalPrice":"9,060,750,346","startPrice":"589.00","highestPrice":"589.00","lowestPrice":"579.00","endPrice":"582.00","upAndDown":"-3.00","sharesTradedNum":"16,504"},{"stockCode":"2330","date":"112-07-06","transactVolume":"32,069,711","totalPrice":"18,234,491,768","startPrice":"573.00","highestPrice":"574.00","lowestPrice":"565.00","endPrice":"565.00","upAndDown":"-17.00","sharesTradedNum":"60,108"},{"stockCode":"2330","date":"112-07-07","transactVolume":"19,858,943","totalPrice":"11,244,712,238","startPrice":"565.00","highestPrice":"572.00","lowestPrice":"563.00","endPrice":"565.00","upAndDown":" 0.00","sharesTradedNum":"21,855"},{"stockCode":"2330","date":"112-07-10","transactVolume":"18,996,089","totalPrice":"10,794,393,087","startPrice":"567.00","highestPrice":"573.00","lowestPrice":"565.00","endPrice":"565.00","upAndDown":" 0.00","sharesTradedNum":"17,792"},{"stockCode":"2330","date":"112-07-11","transactVolume":"18,566,571","totalPrice":"10,665,245,909","startPrice":"574.00","highestPrice":"577.00","lowestPrice":"570.00","endPrice":"577.00","upAndDown":"+12.00","sharesTradedNum":"17,795"},{"stockCode":"2330","date":"112-07-12","transactVolume":"16,220,006","totalPrice":"9,338,600,582","startPrice":"574.00","highestPrice":"578.00","lowestPrice":"572.00","endPrice":"578.00","upAndDown":"+1.00","sharesTradedNum":"14,426"},{"stockCode":"2330","date":"112-07-13","transactVolume":"26,878,397","totalPrice":"15,790,086,719","startPrice":"587.00","highestPrice":"590.00","lowestPrice":"585.00","endPrice":"585.00","upAndDown":"+7.00","sharesTradedNum":"32,540"},{"stockCode":"2330","date":"112-07-14","transactVolume":"24,381,177","totalPrice":"14,372,679,526","startPrice":"589.00","highestPrice":"591.00","lowestPrice":"587.00","endPrice":"591.00","upAndDown":"+6.00","sharesTradedNum":"26,789"},{"stockCode":"2330","date":"112-07-17","transactVolume":"14,311,753","totalPrice":"8,428,551,949","startPrice":"588.00","highestPrice":"591.00","lowestPrice":"587.00","endPrice":"591.00","upAndDown":" 0.00","sharesTradedNum":"18,226"},{"stockCode":"2330","date":"112-07-18","transactVolume":"22,022,410","totalPrice":"12,866,196,578","startPrice":"587.00","highestPrice":"588.00","lowestPrice":"580.00","endPrice":"581.00","upAndDown":"-10.00","sharesTradedNum":"31,079"},{"stockCode":"2330","date":"112-07-19","transactVolume":"24,909,638","totalPrice":"14,517,904,258","startPrice":"584.00","highestPrice":"587.00","lowestPrice":"579.00","endPrice":"581.00","upAndDown":" 0.00","sharesTradedNum":"20,069"},{"stockCode":"2330","date":"112-07-20","transactVolume":"15,676,734","totalPrice":"9,102,325,307","startPrice":"580.00","highestPrice":"584.00","lowestPrice":"578.00","endPrice":"579.00","upAndDown":"-2.00","sharesTradedNum":"19,382"},{"stockCode":"2330","date":"112-07-21","transactVolume":"52,275,128","totalPrice":"29,291,080,481","startPrice":"560.00","highestPrice":"563.00","lowestPrice":"557.00","endPrice":"560.00","upAndDown":"-19.00","sharesTradedNum":"86,810"},{"stockCode":"2330","date":"112-07-24","transactVolume":"27,656,825","totalPrice":"15,469,188,055","startPrice":"557.00","highestPrice":"563.00","lowestPrice":"557.00","endPrice":"558.00","upAndDown":"-2.00","sharesTradedNum":"30,269"},{"stockCode":"2330","date":"112-07-25","transactVolume":"22,767,807","totalPrice":"12,899,067,927","startPrice":"561.00","highestPrice":"569.00","lowestPrice":"561.00","endPrice":"569.00","upAndDown":"+11.00","sharesTradedNum":"21,119"},{"stockCode":"2330","date":"112-07-26","transactVolume":"15,147,698","totalPrice":"8,586,520,667","startPrice":"569.00","highestPrice":"571.00","lowestPrice":"563.00","endPrice":"566.00","upAndDown":"-3.00","sharesTradedNum":"21,679"},{"stockCode":"2330","date":"112-07-27","transactVolume":"13,004,888","totalPrice":"7,396,939,592","startPrice":"570.00","highestPrice":"570.00","lowestPrice":"566.00","endPrice":"569.00","upAndDown":"+3.00","sharesTradedNum":"12,672"},{"stockCode":"2330","date":"112-07-28","transactVolume":"19,009,675","totalPrice":"10,814,265,557","startPrice":"569.00","highestPrice":"573.00","lowestPrice":"565.00","endPrice":"567.00","upAndDown":"-2.00","sharesTradedNum":"19,313"},{"stockCode":"2330","date":"112-07-31","transactVolume":"28,409,339","totalPrice":"16,044,638,425","startPrice":"575.00","highestPrice":"575.00","lowestPrice":"560.00","endPrice":"565.00","upAndDown":"-2.00","sharesTradedNum":"28,425"},{"stockCode":"2330","date":"112-08-01","transactVolume":"18,916,866","totalPrice":"10,711,815,419","startPrice":"565.00","highestPrice":"568.00","lowestPrice":"564.00","endPrice":"567.00","upAndDown":"+2.00","sharesTradedNum":"13,827"}]'