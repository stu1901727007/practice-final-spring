let app = null;

Handlebars.partials = Handlebars.templates;

Handlebars.registerHelper('times', function (n, block) {
    var accum = '';
    for (var i = 0; i < n; ++i)
        accum += block.fn(i);
    return accum;
});

Handlebars.registerHelper('ifeq', function (a, b, options) {
    if (a == b) {
        return options.fn(this);
    }
    return options.inverse(this);
});

Handlebars.registerHelper('ifnoteq', function (a, b, options) {
    if (a != b) {
        return options.fn(this);
    }
    return options.inverse(this);
});

/**
 *
 */
const Utils = {

    /**
     *
     * @param items
     * @returns {[]}
     */
    normalise: function (items) {
        let list = {};
        items.forEach(function (item, key) {

            let date = new Date(item.publicationDate);

            let i = {
                'image': item.photosImagePath,
                'title': item.title,
                'description': item.resume,
                'description_full': item.text,
                'media_type': item.type,
                'media_id': item.id,
                'date_created': date.toDateString(),
                'center': item.agency.name,
                'media': item.embed,
            };

            list[i.media_id] = i;
        });

        return list;
    },

    /**
     *
     * @param text
     * @param length
     * @returns {string|*}
     */
    cutText: function (text, length) {
        if (text == null) {
            return "";
        }
        if (text.length <= length) {
            return text;
        }

        text = text.substring(0, length).trim();
        return text + "...";
    }
}
