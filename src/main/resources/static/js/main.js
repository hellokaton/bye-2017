const COOKIE_SUBMIT_NAME = 'U_ARE_SUBMIT_';

(function (e) {
    function d(a, b) {
        for (var c in b) b.hasOwnProperty(c) && (a[c] = b[c]);
        return a
    }

    function b(a) {
        this.options = d({}, this.options);
        d(this.options, a);
        this._init()
    }

    b.prototype.options = {
        wrapper: document.body,
        message: "yo!",
        layout: "growl",
        effect: "slide",
        type: "error",
        ttl: 6E3,
        onClose: function () {
            return !1
        },
        onOpen: function () {
            return !1
        }
    };
    b.prototype._init = function () {
        this.ntf = document.createElement("div");
        this.ntf.className = "ns-box ns-" + this.options.layout + " ns-effect-" + this.options.effect + " ns-type-" + this.options.type;
        this.ntf.innerHTML = '<div class="ns-box-inner">' + this.options.message + '</div><span class="ns-close"></span></div>';
        this.options.wrapper.insertBefore(this.ntf, this.options.wrapper.firstChild);
        var a = this;
        this.dismissttl = setTimeout(function () {
            a.active && a.dismiss()
        }, this.options.ttl);
        this._initEvents()
    };
    b.prototype._initEvents = function () {
        var a = this;
        this.ntf.querySelector(".ns-close").addEventListener("click", function () {
            alert(22);
            a.dismiss()
        })
    };
    b.prototype.show = function () {
        this.active = !0;
        this.ntf.classList.remove("ns-hide");
        this.ntf.classList.add("ns-show");
        this.options.onOpen()
    };
    b.prototype.dismiss = function () {
        var a = this;
        this.active = !1;
        clearTimeout(this.dismissttl);
        this.ntf.classList.remove("ns-show");
        setTimeout(function () {
            a.ntf.classList.add("ns-hide");
            a.options.onClose()
        }, 25);
        var b = function (c) {
            if (c.target !== a.ntf) return !1;
            this.removeEventListener("animationend", b);
            a.options.wrapper.removeChild(this)
        };
        this.ntf.addEventListener("animationend", b)
    };
    e.NotificationFx = b
})(window);

$(document).ready(function () {
    localStorage && localStorage.clear();

    $("#loc_city").select2();

    const vm = new Vue({
        el: '#app',
        data: {
            page: 1,
            records: [],
            loading: true
        },
        methods: {
            queryRecords: function () {
                var _this = this;
                _this.loading = true;
                axios.get("/records/" + this.page)
                    .then(function (response) {
                        $('#app .loader').hide();
                        var list = response.data.payload;
                        if (list.length == 0) {
                            $('.mdui-snackbar').remove();
                            mdui.snackbar({
                                message: '没有数据了~'
                            });
                            return;
                        }
                        _this.records = _this.records.concat(list);
                        _this.loading = false;
                    }).catch(function (error) {
                    console.log(error);
                });
            },
            doStar: function (id, event) {
                if (event) {
                    axios.post('/star/' + id)
                        .then(function (response) {
                            if (response.data && response.data.success) {
                                var star = $(event.target).parents('.card-footer').find('.star').text();
                                $(event.target).parents('.card-footer').find('.star').text(parseInt(star) + 1);
                                mdui.snackbar({
                                    message: '为他点赞成功'
                                });
                            } else {
                                mdui.snackbar({
                                    message: response.data.msg || '点赞失败'
                                });
                            }
                        }).catch(function (error) {
                        console.log(error);
                    });
                }
            },
            loadMore: function (event) {
                this.page += 1;
                this.queryRecords();
            },
            reload: function () {
                var _this = this;
                axios.get("/records/" + this.page)
                    .then(function (response) {
                        var list = response.data.payload;
                        if (list.length == 0) {
                            $('.mdui-snackbar').remove();
                            mdui.snackbar({
                                message: '没有数据了~'
                            });
                            return;
                        }
                        _this.records = list;
                    }).catch(function (error) {
                    console.log(error);
                });
            }
        },
        mounted: function(){
            this.queryRecords();
        }
    });

    var recordDialog = new mdui.Dialog('#record-dialog');
    $('a.talk-about').click(function () {
        recordDialog.open();
    });

    $('.mdui-dialog-actions #record-btn').click(function () {
        if (getCookie(COOKIE_SUBMIT_NAME)) {
            mdui.snackbar({
                message: '您已经提交过，明天再来吧!'
            });
            return;
        }

        var params = objectifyForm($('.mdui-dialog-content form').serializeArray());
        $.ajax({
            url: "/record",
            type: "POST",
            data: JSON.stringify(params),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (result) {
                if (result && result.success) {
                    localStorage && localStorage.clear();
                    mdui.snackbar({
                        message: '记录成功'
                    });
                    recordDialog.close();
                    // 重新加载数据
                    vm.reload();
                } else {
                    mdui.snackbar({
                        message: result.msg || '记录失败'
                    });
                }
            }
        });
    });

    // event
    var dialog = document.getElementById('record-dialog');
    dialog.addEventListener('opened.mdui.dialog', function () {
        $('.mdui-dialog-content form').sisyphus({
            locationBased: true,
            timeout: 3,
            onSave: function () {
                console.log("Auto saved: ", new Date());
            }
        });
    });

    if($(window).width() < 600){
        $('#cplayer').parent().remove();
    }
});

function objectifyForm(formArray) {
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}

function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
        "SymbianOS", "Windows Phone",
        "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}