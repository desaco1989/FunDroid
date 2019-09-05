;(function(win, doc, $) {
    'use strict';
    var handle = {
        init: function() {
            if(!handle.isMobile()){
                window.location.href = 'index.html';
            }
        },
        isMobile: function(){
            return (navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i));
        }
    };
    $(win).ready(function() {
        handle.init();
    });
    $(win).resize(function() {
        handle.init();
    });
})(window, document, jQuery || $);