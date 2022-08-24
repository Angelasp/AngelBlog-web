
$(function() {
    validateRule();
	$('.images-code').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".images-code").attr("src", url);
	});
});

$.validator.setDefaults({
    submitHandler: function() {
		login();
    }
});

function login() {
	$.modal.loading($("#btnSubmit").data("loading"));
	var username = $("input[name='username']").val().trim();
    var password = $("input[name='password']").val().trim();
    var validateCode = $("input[name='validateCode']").val();
    var rememberuser = $("input[name='rememberuser']").is(':checked');
    $.ajax({
        type: "post",
        url: ctx + "admin/login",
        data: {
            "username": username,
            "password": password,
            "validateCode" : validateCode,
            "rememberMe": rememberuser
        },
        success: function(r) {
            if (r.code == 0) {
                location.href = ctx + 'admin/index';
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}
