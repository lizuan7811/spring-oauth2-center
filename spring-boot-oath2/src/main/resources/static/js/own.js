// let url = 'http://192.168.126.134/findhist/getStock';  // 替換為您的 API 網址
// // let url = 'http://localhost:9999/findhist/getStock';  // 替換為您的 API 網址

// let stockDatas;

// d3.json(`${url}`)
//   .then(response => {
//     // 在這裡處理回應
//     console.log("Success");
//     var myDiv = document.getElementById('append');
//     // 替換 HTML 內容
//     // myDiv.innerHTML = response[0]['transactVolume'];
//     stockDatas = response;
//     console.log(response);
//   })
//   .catch(error => {
//     // 在這裡處理錯誤
//     console.error(error);
//   });

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