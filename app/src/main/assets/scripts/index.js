;(function(win, doc, $) {
    'use strict';
    var selector = {
        container: '#wi-container',
        video: '#wi-video'
    };
    var handle = {
        init: function() {
            if(handle.isMobile()){
                window.location.href = 'mobile.html';
            }else{
                var container = $(selector.container),
                    video = $(selector.video),
                    ratio = {
                        width: 2.25,
                        height: 2
                    },
                    total = {
                        width: container.width(),
                        height: container.height()
                    },
                    element = {
                        width: Math.ceil(total.width / ratio.width),
                        height: Math.ceil(total.height / ratio.height)
                    };
                video.css({width: element.width, height: element.height});
                if(total.height > element.height){
                    var top = Math.ceil(total.height - element.height) / 2;
                    video.css({top: top});
                }
            }
        },
        isMobile: function(){
            return (navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i));
        }
    };
    $(doc).ready(function() {
        handle.init();
        $(selector.video).removeAttr('poster');
    });
    $(win).resize(function(){
        handle.init();
    });
})(window, document, jQuery || $);