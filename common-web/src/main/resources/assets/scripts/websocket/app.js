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
    var socket = new SockJS('/ws/user');
    stompClient = Stomp.over(socket);
    //传递连接时的自定义参数
    stompClient.connect({'userId':userId}, function (frame) {
        //连接建立成功的回调
        setConnected(true);
        console.log('Connected: ' + frame);
        //单播
        stompClient.subscribe('/user/single/notify', function (greeting) {
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
function greetTo() {
    stompClient.send("/app/sendTo", {}, JSON.stringify({'userId': $("#targetUserId").val()}));
}
function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#greet" ).click(function() { greetTo(); });
});