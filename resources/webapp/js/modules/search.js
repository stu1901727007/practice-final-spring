/**
 * Управлява полетата за търсене и филтриране
 */
class Search {

    /**
     * Search constructor
     */
    constructor() {

        this.timerSearch = null;
        this.nasaCenters = {};

        this.init();
    }

    /**
     *
     * @returns {Search}
     */
    init() {

        let that = this;

        app.api.callAgency(that.setAgency.bind(this));

        /**
         * Превенция за изпращане на формата
         */
        $(document).on('submit', '.filter-form', function (e) {
            e.preventDefault();
        });

        /**
         * При писане във формата
         */
        $(document).on('keyup', '.filter-form input[name="search"]', function (e) {

            clearTimeout(that.timerSearch);

            that.timerSearch = setTimeout(that.search.bind(that), 1000)

            if (e.which == 13) {
                clearTimeout(that.timerSearch);
                that.search();
            }
        });

        /**
         * При натискане на търсене
         */
        $(document).on('click', '.filter-form .btn-search', function (e) {

            clearTimeout(that.timerSearch);
            that.search();
        });

        /**
         * Детайлно търсене
         */
        $(document).on('click', '.advanced-search', function (e) {

            e.preventDefault();

            clearTimeout(that.timerSearch);
            that.showAdvanceFilter();

        });

        /**
         * Смяна на изгледа
         */
        $(document).on('click', '.switch .btn', function (e) {

            e.preventDefault();

            $('.switch .btn').toggleClass('btn-info').toggleClass('btn-light');

            let gridItem = $('.results-container .grid-item'),
                img = gridItem.find('.img-container'),
                caption = gridItem.find('.caption');

            gridItem.toggleClass('col-lg-6 col-xl-4');
            img.toggleClass('col-md-5');
            caption.toggleClass('col-md-7');

            $('.results-container').masonry('layout');
        });

        return this;
    }

    /**
     *
     * @returns {Search}
     */
    search() {

        try {
            app.api.call(this.prepareSearchObject(), app.loadResults.bind(app));
        } catch (err) {
        }

        return this;
    }

    /**
     *
     * @returns {{q: (jQuery|string|undefined), media_type: *}}
     */
    prepareSearchObject() {

        let checkedVals = $('.filter-form input[name="media_type[]"]:checked').map(function () {
            return this.value;
        }).get();

        let criteria = {
            'q': $('.filter-form input[name="search"]').val(),
            'center': $('.filter-form select[name="center"]').val(),
            'year_start': $('.filter-form input[name="years-from"]').val(),
            'year_end': $('.filter-form input[name="years-to"]').val(),
            'media_type': checkedVals.join(",")
        };

        return criteria;
    }

    setAgency(list)
    {
        this.nasaCenters = list;
    }

    /**
     *
     * @returns {Search}
     */
    showAdvanceFilter() {

        alert('da');


        console.log( this.nasaCenters );

        $('.filter-wrapper form').html(Handlebars.templates.extendedFilter({'centers': this.nasaCenters}));

        this.initAdvanced();

        return this;
    }

    /**
     *
     * @returns {Search}
     */
    showSimpleFilter() {
        $('.filter-wrapper form').html(Handlebars.templates.mainFilter());

        return this;
    }

    /**
     *
     * @returns {Search}
     */
    initAdvanced() {
        let d = new Date();

        $("#ranges").html(yearsRange.min + "г. - " + yearsRange.max + "г.");
        $("#years-from").val(yearsRange.min);
        $("#years-to").val(yearsRange.max);

        $("#year-range").slider({
            range: true,
            min: yearsRange.min,
            max: yearsRange.max,
            values: [yearsRange.min, yearsRange.max],
            slide: function (event, ui) {
                $("#years-from").val(ui.values[0]);
                $("#years-to").val(ui.values[1]);

                $("#ranges").html(ui.values[0] + "г. - " + ui.values[1] + "г.");
            }
        });

        return this;
    }
}
