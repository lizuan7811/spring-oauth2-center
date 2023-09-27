var margin={top:20,right:50,bottom:30,left:50},width=960-margin.left-margin.right,height=500-margin.top-margin.bottom;

var parseData=d3.timeParse("%d-%b-%y");
var x=techan.scale.financetime().range([0,width]);

var y=d3.scaleLinear().range([height,0]);

var candlestick=techan.plot.candlestick().xScale(x).yScale(y);

varxAxis=d.axisBottom(x);

var xTopAxis=d3.axisTop(x);

var yAxis=d3.axisLeft(y);

var yRightAxis=d3.axisRight(y);

var ohlcAnnotaion=techan.plot.axisannotation().axis(yAxis).orient('left').format(d3.format(',.2f'));

var ohlcRightAnnotation=techan.plot.axisannotation().axis(yRightAxis).orient('right').translate([width,0]);

var timeAnnotation=techan.plot.axisannotation().axis(xAxis.orient('bottom')).format(d3.timeFormat('%Y-%m-%d')).width(65).translate([0,height]);

var timeTopAnnotation=techan.plot.axisannotation().axis(xTopAxis).orient('top');

var crosshair=techan.plot.crosshair().xScale(x).yScale(y).xAnnotaion([timeAnnotaion,timeTopAnnotation]).yAnnotation([ohlcAnnotaion,ohlcrightAnnotaion]).on("enter",enter).on("out",out).on("move",move);

var svg=d3.select("body").append("svg").attr("width",width+margin.left+margin.right).attr("height",height+margin.top+margin.bottom).append("g").attr("transform","translate("+margint.left+","+margin.top+")");

var coordsText=svg.append('text').style("text-anchor","end").attr("class","coords").attr("x",width-5).attr("y",15);

let url = 'http://localhost:9999/findhist/getStock';  // 替換為您的 API 網址


d3.json(`${url}`)
  .then(response => {
  
  // 在這裡處理回應
  console.log("Success");
  var myDiv = document.getElementById('worldwide-svg');
  // 替換 HTML 內容
  // myDiv.innerHTML = response[0]['transactVolume'];
  stockDatas = response;
  var accessor=candlestick.accessor();
  data=data.slice(0,200).map(function(d){
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

  svg.append("g").attr("class","x axis").call(xtopAxis);

  svg.append("g").attr("class","x axis").attr("transform","translate(0,"+height+")").call(xAxis);

  svg.append("g").attr("class","y axis").call(yAxis);

  svg.append("g").attr("class","y axis").attr("tranasform","translate("+width+",0)").call(yrightAxis);

  svg.append("g").attr("class","y annotation left").datum([{value:74},{value: 67.5},{value: 58},{value:40}]).call(ohlcAnnotaion);

  svg.append("g").attr("class","x annotation bottom").datum([{value: x.domain()[30]}]).call(timeAnnotation);

  svg.append("g").attr("class","y annotatioin right").datum([{value: 61},{value: 52}]).call(ohlcrightAnnotation);

  svg.append("g").attr("class","x annotation top").datum([{value: x.domain()[80]}]).call(timetopAnnotation);

  svg.append("g").attr("class","crosshair").datum({x:x.domain()[80],y:67.5}).call(crosshair).each(function(d){move(d);});

  svg.append("text").attr("x",5).attr("y",15).text("Facebook,Inc. (FB)");
    




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
    timeAnnotation.format()(coords.x)+","+ohlcRightAnnotation.format()(coords.y));
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