/*
Template Name: Material Pro Admin
Author: Themedesigner
Email: niravjoshi87@gmail.com
File: js
*/
$(document).ready(function () {

    $(".preloader").fadeOut();

    $('.datepicker-autoclose').datepicker({
        format: 'dd/mm/yyyy',
        autoclose: true,
        zIndexOffset: 5000
    });

    $(document).on('click', '.btn-delete-record', function (e) {

        e.preventDefault();

        var that = $(this),
            buttonSubmit = that.closest('form').find('.btnSubmit');

        Swal.fire({
            title: "Изтриване на запис",
            text: "Сигурни ли сте, че искате да изтриете този запис?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Да, изтрий!",
            cancelButtonText: "Не"
        }).then(function (result) {

            if (result.value) {

                that.closest('form').find('input[name="_method"]').val('DELETE')

                buttonSubmit.trigger('click');
            }

            Swal.close();
        });

    });


    $(document).on('click', '.btn-remove-image', function (e) {

        e.preventDefault();

        var that = $(this);

        Swal.fire({
            title: "Изтриване на изображение",
            text: "Сигурни ли сте, че искате да изтриете това изображение?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Да, изтрий!",
            cancelButtonText: "Не"
        }).then(function (result) {

            if (result.value) {

                enpoint = '/bo/library/' + $('#libraryId').val() + '/image';

                $.ajax({
                    url: enpoint,
                    dataType: 'json',
                    method: 'DELETE'
                }).done(function (result) {

                    if( result.status )
                    {
                        $('.image-preview').fadeOut();
                    }
                }).fail(function () {
                    window.location.reload();
                });

            }

            Swal.close();
        });

    });


    // ============================================================== 
    // This is for the top header part and sidebar part
    // ==============================================================  
    var set = function () {
        var width = (window.innerWidth > 0) ? window.innerWidth : this.screen.width;
        var topOffset = 70;
        if (width < 1170) {
            $("body").addClass("mini-sidebar");
            $('.navbar-brand span').hide();
            $(".scroll-sidebar, .slimScrollDiv").css("overflow-x", "visible").parent().css("overflow", "visible");
            $(".sidebartoggler i").addClass("ti-menu");
        } else {
            $("body").removeClass("mini-sidebar");
            $('.navbar-brand span').show();
            //$(".sidebartoggler i").removeClass("ti-menu");
        }

        var height = ((window.innerHeight > 0) ? window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $(".page-wrapper").css("min-height", (height) + "px");
        }
    };
    $(window).ready(set);
    $(window).on("resize", set);

    // topbar stickey on scroll
    $(".fix-header .topbar").stick_in_parent({});

    // this is for close icon when navigation open in mobile view
    $(".nav-toggler").click(function () {
        $("body").toggleClass("show-sidebar");
        $(".nav-toggler i").toggleClass("ti-menu");
        $(".nav-toggler i").addClass("ti-close");
    });
    $(".sidebartoggler").on('click', function () {
        //$(".sidebartoggler i").toggleClass("ti-menu");
    });
    $(".search-box a, .search-box .app-search .srh-btn").on('click', function () {
        $(".app-search").toggle(200);
    });

    // ============================================================== 
    // Auto select left navbar
    // ============================================================== 

        var url = window.location;
        var element = $('ul#sidebarnav a').filter(function () {
            return this.href == url;
        }).addClass('active').parent().addClass('active');
        while (true) {
            if (element.is('li')) {
                element = element.parent().addClass('in').parent().addClass('active');
            } else {
                break;
            }
        }

    // ============================================================== 
    //tooltip
    // ============================================================== 

        $('[data-toggle="tooltip"]').tooltip()

    // ==============================================================
    // Sidebarmenu
    // ==============================================================

        $('#sidebarnav').metisMenu();

    // ============================================================== 
    // Slimscrollbars
    // ============================================================== 
    $('.scroll-sidebar').slimScroll({
        position: 'left',
        size: "5px",
        height: '100%',
        color: '#dcdcdc'
    });
    // ============================================================== 
    // Resize all elements
    // ============================================================== 
    $("body").trigger("resize");
});
