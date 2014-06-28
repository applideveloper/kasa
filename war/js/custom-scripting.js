//jQuery Mobile初期設定
$(document).on("mobileinit", function() {

    //デフォルトのテーマ指定
    $.mobile.page.prototype.options.headerTheme  = "b";
    $.mobile.page.prototype.options.contentTheme = "c";
    $.mobile.page.prototype.options.footerTheme  = "b";

    //ページ遷移のトランジション（アニメーション）効果を停止
    $.mobile.defaultPageTransition = "none";

    //Ajax機能を無効
    $.mobile.ajaxEnabled = false;

});
