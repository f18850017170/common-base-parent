var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var userId = $('#userId').val();//TODO 需要去获取登录用户的id
    //连接的webSocket服务器
    var socket = new SockJS('/ws/merchant');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        //单播
        stompClient.subscribe('/user/merchant/notify', function (greeting) {
            showGreeting(greeting.body);
        },{'userId':userId});
        //广播
        stompClient.subscribe('/topic/notify/message', function(message){
            showGreeting(message.body);
        },{'userId':userId});
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    var obj = JSON.parse(message);
    console.log(obj);
    if(obj instanceof Array){
        obj.forEach(function(item){
            $("#greetings").append("<tr><td id='"+item.notifyMsgKey+"' data='"+item.msg.userId+"'><a>" + item.msg.content + "</a></td>/tr>");
            document.getElementById(item.notifyMsgKey).addEventListener('click',function(evt){
                stompClient.send("/app/notifyMsg/confirm", {}, JSON.stringify({'userId': $("#"+item.notifyMsgKey).attr('data'),'notifyMsgKey':item.notifyMsgKey}));
            },false);
        });
    }else {
        $("#greetings").append("<tr><td id='"+obj.notifyMsgKey+"' data='"+obj.msg.userId+"'><a>" + obj.msg.content + "</a></td></tr>");
        document.getElementById(obj.notifyMsgKey).addEventListener('click',function(evt){
            stompClient.send("/app/notifyMsg/confirm", {}, JSON.stringify({'userId': $("#"+obj.notifyMsgKey).attr('data'),'notifyMsgKey':obj.notifyMsgKey}));
        },false);
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});