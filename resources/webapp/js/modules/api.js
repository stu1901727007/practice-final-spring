/**
 * Api NASA
 */
class Api {

    /**
     * Api constructor
     * @param param
     */
    constructor() {

        this.cache = new CacheApi();

        this.endpointUrl = '/api/v1';
        this.search = '/search';
        this.media = '/library/';
        this.agency = '/agency';
        this.home = '/library/top';
    }

    /**
     *
     * @param criteria
     * @param callback
     * @returns {Api|boolean}
     */
    call(criteria, callback) {

        let that = this;

        app.setHtmlContainer(Handlebars.templates.placeholderItems());

        if (typeof callback !== 'function') {
            throw new ValidationError("Системен проблем!");
        }

        if (criteria.q == undefined || criteria.q.length <= 0) {
            throw new ValidationError("Няма критерий на търсене!");
        }

        if (criteria.page == undefined)
            criteria.page = 1;

        if (criteria.media_type == undefined)
            criteria.media_type = 'images,video,audio';

        let query = [];
        query.push('page=' + criteria.page);
        query.push('q=' + criteria.q);
        query.push('media_type=' + criteria.media_type);

        if (criteria.center !== undefined)
            query.push('center=' + criteria.center);

        if (criteria.year_start !== undefined)
            query.push('year_start=' + criteria.year_start);

        if (criteria.year_end !== undefined)
            query.push('year_end=' + criteria.year_end);


        const cache = this.cache.getSearch(query);

        if (cache !== null) {
            callback(cache);
            return true;
        }

        const enpoint = this.endpointUrl + this.search + '?' + query.join('&');

        $.ajax({
            url: enpoint,
            dataType: 'json'
        }).done(function (result) {

            const items = Utils.normalise(result.media);

            let data = {
                'items': items,
                'total_hits': result.totalItems
            };

            that.cache.saveSearch(query, data);

            callback(data);

        }).fail(function () {
            throw new ValidationError("Възникна проблем при четене от API! <br/>Опитайте след 5 минути.");
        });

        return this;
    }

    /**
     *
     * @param callback
     * @returns {Api}
     */
    callAgency(callback) {

        const enpoint = this.endpointUrl + this.agency;

        let that = this;

        $.ajax({
            url: enpoint,
            dataType: 'json'
        }).done(function (result) {

            let nasaCenters = {};
            if( result.list.length > 0 )
            {
                for(let x in result.list)
                {
                    nasaCenters[ result.list[x].id ] = result.list[x].name;
                }
            }

            callback(nasaCenters);

        }).fail(function () {
            throw new ValidationError("Възникна проблем при четене от API! <br/>Опитайте след 5 минути.");
        });

        return this;
    }

    /**
     *
     * @param callback
     * @returns {Api}
     */
    callHomeTop(callback) {

        const enpoint = this.endpointUrl + this.home;

        $.ajax({
            url: enpoint,
            dataType: 'json'
        }).done(function (result) {

            const items = Utils.normalise(result.media);

            let data = {
                'items': items,
                'total_hits': result.totalItems
            };

            console.log( data );

            callback(data);

        }).fail(function () {
            throw new ValidationError("Възникна проблем при четене от API! <br/>Опитайте след 5 минути.");
        });

        return this;
    }
}
