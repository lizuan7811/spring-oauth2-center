$(function () {


    var authorizeToken=document.cookie;
    console.log(">>> "+authorizeToken);

    $("#csrfToken").attr("value", getCookiesItem('XSRF-TOKEN'));

    var resourcesUrl = 'http://localhost:8082/call/getStock';

    console.log(">>> "+resourcesUrl);

    console.log(">>> "+getCookiesItem('access_token'));

    $.ajax({
        url: resourcesUrl, // 后端处理的 URL
        type: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest', // 添加 X-Requested-With 头部
            'XSRF-TOKEN': getCookiesItem('XSRF-TOKEN'),
            'Authorization': "Bearer "+ getCookiesItem('access_token')
            // 在这里添加其他需要的请求头部信息
        },
        contentType: 'application/json',
        success: function(response) {
            // 请求成功的处理逻辑
            console.log('Request successful');
            console.log(response);
            var stockDatas = response;
            d3show(stockDatas);
        },
        error: function(xhr, status, error) {
            // 请求失败的处理逻辑
            console.error('Request failed:', error);
            alert('Request failed. Please try again.');
        }
    });


    // 替換為您的 API 網址
    // console.log(url);

    // d3.json(`${resourcesUrl}`, {
    //     method: 'GET',
    //     headers: {
    //         'XSRF-TOKEN': getCookiesItem('XSRF-TOKEN'),
    //         'Authorization': "Bearer "+ getCookiesItem('access_token')
    //     }
    // }).then(function(response){
    //         // 在這裡處理回應
    //         console.log("Success");
    //         // var myDiv = $("#worldwide-svg");
    //         mySvg = document.getElementById('worldwide-svg');
    //
    //         // 替換 HTML 內容
    //         // myDiv.innerHTML = response[0]['transactVolume'];
    //         var stockDatas = response;
    //         d3show(stockDatas);
    //     })
    //     .catch(error => {
    //         // 在這裡處理錯誤
    //         console.error(error);
    //     });

    // $.ajax({
    //     url: resourcesUrl, // 后端登录处理的 URL
    //     type: 'GET',
    //     beforeSend: function(request){
    //         request.setRequestHeader("Authorization","Bearer "+ getCookiesItem("access_token"));
    //         request.setRequestHeader("XSRF-TOKEN"+ getCookiesItem("XSRF-TOKEN"));
    //         request.setRequestHeader("X-Requested-With","XMLHttpRequest");
    //     },
    //     // headers: {
    //     //     // 'Content-Type': 'application/json',
    //     //     'XSRF-TOKEN': getCookiesItem('XSRF-TOKEN'),
    //     //     // 在这里添加其他需要的请求头部信息
    //     //     'Authorization': "Bearer "+ getCookiesItem('access_token')
    //     // },
    //     cache: false,
    //     contentType: 'application/json',
    //     success: function(response) {
    //         // 登录成功后的处理逻辑
    //         console.log('Login successful');
    //         console.log(response);
    //         var stockDatas = response;
    //         d3show(stockDatas);
    //     },
    //     error: function(xhr, status, error) {
    //         // 登录失败后的处理逻辑
    //         console.error('Login failed:', error);
    //         alert('Login failed. Please try again.');
    //     }
    // });

})

let contexturl = $(location).attr('origin')
let mySvg
function getCookiesItem(itemName) {
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies) {
        const [name, value] = cookie.split('=');
        if (itemName===name) {
            return decodeURIComponent(value);
        }
    }
    return null;
}

function clearEle() {
    var svg = d3.select("#worldwide-svg");
    console.log("clear" + svg);

    svg.selectAll('.x-axis').remove();
    svg.selectAll('.y-axis').remove();
}

function d3show(resultData) {
    clearEle();
    var stockDatas=JSON.parse(resultData);
    var element = $("#worldwide-div");
    var element1 = $("#salse-revenue");
    var width = Math.max(element.width(), element1.width());
    var height = Math.max(element.height(), element1.height());
    element.attr('width',width+"px").attr('height',height+"px");
    element1.attr('width',width+"px").attr('height',height+"px");
    const svg = d3.select("#worldwide-svg").attr('width', width).attr('height', height);
    // const svg=d3.select('#svg');
    const margin = { top: 10, right: 30, bottom: 60, left: 70 };
    const innerWidth = width - margin.left - margin.right;
    const innerHeight = height - margin.top - margin.bottom;

    const dateExtent = d3.extent(stockDatas, d => convertRocToDate(d.date));

    const xScale = d3.scaleTime()
        .domain(dateExtent)
        .range([0, innerWidth]);


    const dateFormatter = d3.timeFormat("%Y-%m-%d");


    const xAxis = d3.axisBottom(xScale);

    xAxis.ticks(200).tickFormat(dateFormatter).tickPadding(10);

    // xAxis = d3.axisBottom(xScale)
    // .tickFormat(d3.timeFormat("%Y"))
    // .ticks(d3.timeYear.every(1));

    const g = svg.append('g').attr('id', 'maingroup')
        .attr('transform', `translate(${margin.left},${margin.top})`);

    g.append('g').attr('transform', `translate(0,${innerHeight})`).attr("class", "x-axis").call(xAxis);

    // 設定圖型
    const yScale = d3.scaleLinear()
        .domain([d3.min(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, ""))), d3.max(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, "")))])
        .range([innerHeight, 0]);

    const yAxis = d3.axisLeft(yScale);
    // 顯示軸線
    g.append('g').attr("class", "y-axis").call(yAxis);
    yAxis.ticks(stockDatas.length);

    // 綁定圖型
    const yRect = d3.scaleLinear()
        .domain([d3.min(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, ""))), d3.max(stockDatas, d => parseInt(d.transactVolume.replace(/,/g, "")))])
        .range([innerHeight, 0]);

    // 顯示圖型
    const rects = g.selectAll('rect').data(stockDatas).enter().append('rect')
        // .attr('x',(d,i)=>{return (i+1)*(innerWidth/stockDatas.length)})
        .attr('x', (d, i) => innerWidth / (stockDatas.length * 2) + xScale(convertRocToDate(d.date)))
        .attr('width', 4)
        .attr('height', (d, i) => yRect(parseInt(d.transactVolume.replace(/,/g, ""))))
        .attr('y', (d, i) => innerHeight - yRect(parseInt(d.transactVolume.replace(/,/g, ""))))
        .attr('fill', '#CCFF99')
        .on('mouseover', function () {
            d3.select(this).attr('opacity', '0,5').attr('stroke', '#FFCCFF').attr('fill', '#FFCCFF');
        })
        .on('mouseout', function () {
            d3.select(this).attr('opacity', '0,5').attr('stroke', '#CCFF99').attr('fill', '#CCFF99');
        });

    // 將日期轉角度
    g.selectAll('.x-axis .tick text')
        .attr('transform', 'rotate(-90)')
        .attr('y', innerWidth / (stockDatas.length * 2))
        .style("text-anchor", "end");
 
}

function convertRocToDate(rocDateStr) {
    const [rocYear, month, day] = rocDateStr.split('-');
    const year = parseInt(rocYear) + 1911;
    const date = new Date(year, month - 1, day);
    // console.log(rocDateStr + "=" + date);

    return date;
}

function dynamicsearch(dynamicevent){
    console.log($(dynamicevent).val())

    dynamictext=$(dynamicevent).val().replace("-","").replace("%","").replace(";","");
    console.log(">>>> "+dynamictext);
    $.ajax({
        url: contexturl+"/index/searchstock",
        method: "POST",
        data: dynamictext,
        dataType: "json",
        sucsses(){

        },
        error(){

        }
    });

}