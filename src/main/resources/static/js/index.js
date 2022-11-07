$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");
    var title = $("#recipient-name").val();
    var content = $("#message-text").val();
    // 发送异步请求(POST)
    $.ajax(
        {
            url: CONTEXT_PATH + "/discusspost/save",
            data: {
                title:title,
                content:content
            },
            success: function (data) {
                // 在提示框中显示返回消息
                $("#hintBody").text(data.msg);
                // 显示提示框
                $("#hintModal").modal("show");
                // 2秒后,自动隐藏提示框
                setTimeout(function () {
                    $("#hintModal").modal("hide");
                    // 刷新页面
                    if (data.code == 0) {
                        window.location.reload();
                    }
                }, 2000);
            }
        }
    );

}