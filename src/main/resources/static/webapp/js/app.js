function _typeof(obj) { "@babel/helpers - typeof"; if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); if (superClass) _setPrototypeOf(subClass, superClass); }

function _createSuper(Derived) { var hasNativeReflectConstruct = _isNativeReflectConstruct(); return function _createSuperInternal() { var Super = _getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = _getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return _possibleConstructorReturn(this, result); }; }

function _possibleConstructorReturn(self, call) { if (call && (_typeof(call) === "object" || typeof call === "function")) { return call; } return _assertThisInitialized(self); }

function _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function _wrapNativeSuper(Class) { var _cache = typeof Map === "function" ? new Map() : undefined; _wrapNativeSuper = function _wrapNativeSuper(Class) { if (Class === null || !_isNativeFunction(Class)) return Class; if (typeof Class !== "function") { throw new TypeError("Super expression must either be null or a function"); } if (typeof _cache !== "undefined") { if (_cache.has(Class)) return _cache.get(Class); _cache.set(Class, Wrapper); } function Wrapper() { return _construct(Class, arguments, _getPrototypeOf(this).constructor); } Wrapper.prototype = Object.create(Class.prototype, { constructor: { value: Wrapper, enumerable: false, writable: true, configurable: true } }); return _setPrototypeOf(Wrapper, Class); }; return _wrapNativeSuper(Class); }

function _construct(Parent, args, Class) { if (_isNativeReflectConstruct()) { _construct = Reflect.construct; } else { _construct = function _construct(Parent, args, Class) { var a = [null]; a.push.apply(a, args); var Constructor = Function.bind.apply(Parent, a); var instance = new Constructor(); if (Class) _setPrototypeOf(instance, Class.prototype); return instance; }; } return _construct.apply(null, arguments); }

function _isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Date.prototype.toString.call(Reflect.construct(Date, [], function () {})); return true; } catch (e) { return false; } }

function _isNativeFunction(fn) { return Function.toString.call(fn).indexOf("[native code]") !== -1; }

function _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }

function _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }

var app = null;
Handlebars.partials = Handlebars.templates;
Handlebars.registerHelper('times', function (n, block) {
  var accum = '';

  for (var i = 0; i < n; ++i) {
    accum += block.fn(i);
  }

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

var Utils = {
  /**
   *
   * @param items
   * @returns {[]}
   */
  normalise: function normalise(items) {
    var list = {};
    items.forEach(function (item, key) {
      var date = new Date(item.publicationDate);
      var i = {
        'image': item.photosImagePath,
        'title': item.title,
        'description': item.resume,
        'description_full': item.text,
        'media_type': item.type,
        'media_id': item.id,
        'date_created': date.toDateString(),
        'center': item.agency.name,
        'media': item.embed
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
  cutText: function cutText(text, length) {
    if (text == null) {
      return "";
    }

    if (text.length <= length) {
      return text;
    }

    text = text.substring(0, length).trim();
    return text + "...";
  }
};
/**
 * Error handler
 */

var ValidationError = /*#__PURE__*/function (_Error) {
  _inherits(ValidationError, _Error);

  var _super = _createSuper(ValidationError);

  /**
   * ValidationError constructor
   * @param message
   */
  function ValidationError(message) {
    var _this;

    _classCallCheck(this, ValidationError);

    _this = _super.call(this, message); // this.message = message;
    // this.name = "Error";

    _this.show();

    return _this;
  }
  /**
   *
   * @returns {ValidationError}
   */


  _createClass(ValidationError, [{
    key: "show",
    value: function show() {
      app.setHtmlContainer(Handlebars.templates.error({
        'message': this.message
      }));
      return this;
    }
  }]);

  return ValidationError;
}( /*#__PURE__*/_wrapNativeSuper(Error));
/**
 *
 */


var CacheApi = /*#__PURE__*/function () {
  /**
   * Cache constructor
   */
  function CacheApi() {
    _classCallCheck(this, CacheApi);

    this.useStorage = this.isStorageAvailable();

    if (this.useStorage) {
      this.cacheApi = 'api-calls';

      if (localStorage.getItem(this.cacheApi) === null) {
        localStorage.setItem(this.cacheApi, '{}');
      }

      console.log('Info[localStorage кеш е активен!]');
    } else {
      console.log('Warning[Не се поддържа localStorage и няма да се използва!]');
    }
  }
  /**
   *
   * @param criteria
   * @returns {string|null}
   */


  _createClass(CacheApi, [{
    key: "getSearch",
    value: function getSearch(criteria) {
      if (!this.useStorage) return null;
      var hash = md5(JSON.stringify(criteria));
      var cache = JSON.parse(this.get(this.cacheApi));
      return cache[hash] !== undefined ? cache[hash] : null;
    }
    /**
     *
     * @param criteria
     * @param data
     * @returns {boolean}
     */

  }, {
    key: "saveSearch",
    value: function saveSearch(criteria, data) {
      if (!this.useStorage) return true;
      var hash = md5(JSON.stringify(criteria));
      var cache = JSON.parse(this.get(this.cacheApi));
      cache[hash] = data;

      try {
        this.set(this.cacheApi, JSON.stringify(cache));
      } catch (e) {
        this.clear();
      }

      return true;
    }
    /**
     *
     * @param key
     * @returns {boolean}
     */

  }, {
    key: "has",
    value: function has(key) {
      if (!this.useStorage) return false;
      return localStorage.getItem(key) === null ? false : true;
    }
    /**
     *
     * @param key
     * @param item
     * @returns {void|CacheApi}
     */

  }, {
    key: "set",
    value: function set(key, item) {
      if (!this.useStorage) return localStorage.setItem(key, item);
      return this;
    }
    /**
     *
     * @param key
     * @returns {string|null}
     */

  }, {
    key: "get",
    value: function get(key) {
      if (!this.useStorage) return null;
      return localStorage.getItem(key);
    }
    /**
     *
     * @param key
     */

  }, {
    key: "remove",
    value: function remove(key) {
      return localStorage.removeItem(key);
    }
    /**
     *
     * @returns {boolean}
     */

  }, {
    key: "clear",
    value: function clear() {
      localStorage.clear();
      return true;
    }
    /**
     *
     * @returns {boolean}
     */

  }, {
    key: "isStorageAvailable",
    value: function isStorageAvailable() {
      try {
        var storage = window['localStorage'],
            x = '__storage_test__';
        storage.setItem(x, x);
        storage.removeItem(x);
        return true;
      } catch (e) {}

      return false;
    }
  }]);

  return CacheApi;
}();
/**
 * Api NASA
 */


var Api = /*#__PURE__*/function () {
  /**
   * Api constructor
   * @param param
   */
  function Api() {
    _classCallCheck(this, Api);

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


  _createClass(Api, [{
    key: "call",
    value: function call(criteria, callback) {
      var that = this;
      app.setHtmlContainer(Handlebars.templates.placeholderItems());

      if (typeof callback !== 'function') {
        throw new ValidationError("Системен проблем!");
      }

      if (criteria.q == undefined || criteria.q.length <= 0) {
        throw new ValidationError("Няма критерий на търсене!");
      }

      if (criteria.page == undefined) criteria.page = 1;
      if (criteria.media_type == undefined) criteria.media_type = 'images,video,audio';
      var query = [];
      query.push('page=' + criteria.page);
      query.push('q=' + criteria.q);
      query.push('media_type=' + criteria.media_type);
      if (criteria.center !== undefined) query.push('center=' + criteria.center);
      if (criteria.year_start !== undefined) query.push('year_start=' + criteria.year_start);
      if (criteria.year_end !== undefined) query.push('year_end=' + criteria.year_end);
      var cache = this.cache.getSearch(query);

      if (cache !== null) {
        callback(cache);
        return true;
      }

      var enpoint = this.endpointUrl + this.search + '?' + query.join('&');
      $.ajax({
        url: enpoint,
        dataType: 'json'
      }).done(function (result) {
        var items = Utils.normalise(result.media);
        var data = {
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

  }, {
    key: "callAgency",
    value: function callAgency(callback) {
      var enpoint = this.endpointUrl + this.agency;
      var that = this;
      $.ajax({
        url: enpoint,
        dataType: 'json'
      }).done(function (result) {
        var nasaCenters = {};

        if (result.list.length > 0) {
          for (var x in result.list) {
            nasaCenters[result.list[x].id] = result.list[x].name;
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

  }, {
    key: "callHomeTop",
    value: function callHomeTop(callback) {
      var enpoint = this.endpointUrl + this.home;
      $.ajax({
        url: enpoint,
        dataType: 'json'
      }).done(function (result) {
        var items = Utils.normalise(result.media);
        var data = {
          'items': items,
          'total_hits': result.totalItems
        };
        console.log(data);
        callback(data);
      }).fail(function () {
        throw new ValidationError("Възникна проблем при четене от API! <br/>Опитайте след 5 минути.");
      });
      return this;
    }
  }]);

  return Api;
}();
/**
 * Навигация
 */


var Navigation = /*#__PURE__*/function () {
  /**
   * Navigation constructor
   */
  function Navigation() {
    _classCallCheck(this, Navigation);

    this.init();
  }
  /**
   *
   * @returns {Navigation}
   */


  _createClass(Navigation, [{
    key: "init",
    value: function init() {
      /**
       * Управлява лявата колона
       */
      $(document).on('click', '#sidebarCollapse', function () {
        $('#sidebar').toggleClass('active');
      });
      $(document).on('click', '.lnk-home', function (e) {
        e.preventDefault();
        app.prepareHome();
      });
      /**
       * Оперира с базови линкове
       */

      $(document).on('click', '.lnk-modal', function (e) {
        e.preventDefault();
        var page = $(this).data('page'),
            modal = Handlebars.templates.modal;
        var html = '';

        switch (page) {
          case 'about':
            html = modal({
              'title': 'За проекта',
              'body': Handlebars.templates.about()
            });
            break;

          case 'credits':
            html = modal({
              'title': 'Кредити',
              'body': Handlebars.templates.credits()
            });
            break;
        }

        $('body').append(html);
        $('#innerModal').modal('show');
        /**
         * Премахваме модалния прозорец
         */

        $('#innerModal').one('hidden.bs.modal', function () {
          $(this).remove();
        });
      });
      return this;
    }
  }]);

  return Navigation;
}();
/**
 * Управлява полетата за търсене и филтриране
 */


var Search = /*#__PURE__*/function () {
  /**
   * Search constructor
   */
  function Search() {
    _classCallCheck(this, Search);

    this.timerSearch = null;
    this.nasaCenters = {};
    this.init();
  }
  /**
   *
   * @returns {Search}
   */


  _createClass(Search, [{
    key: "init",
    value: function init() {
      var that = this;
      app.api.callAgency(that.setAgency);
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
        that.timerSearch = setTimeout(that.search.bind(that), 1000);

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
        var gridItem = $('.results-container .grid-item'),
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

  }, {
    key: "search",
    value: function search() {
      try {
        app.api.call(this.prepareSearchObject(), app.loadResults.bind(app));
      } catch (err) {}

      return this;
    }
    /**
     *
     * @returns {{q: (jQuery|string|undefined), media_type: *}}
     */

  }, {
    key: "prepareSearchObject",
    value: function prepareSearchObject() {
      var checkedVals = $('.filter-form input[name="media_type[]"]:checked').map(function () {
        return this.value;
      }).get();
      var criteria = {
        'q': $('.filter-form input[name="search"]').val(),
        'center': $('.filter-form select[name="center"]').val(),
        'year_start': $('.filter-form input[name="years-from"]').val(),
        'year_end': $('.filter-form input[name="years-to"]').val(),
        'media_type': checkedVals.join(",")
      };
      return criteria;
    }
  }, {
    key: "setAgency",
    value: function setAgency(list) {
      this.nasaCenters = list;
    }
    /**
     *
     * @returns {Search}
     */

  }, {
    key: "showAdvanceFilter",
    value: function showAdvanceFilter() {
      console.log(this.nasaCenters);
      $('.filter-wrapper form').html(Handlebars.templates.extendedFilter({
        'centers': this.nasaCenters
      }));
      this.initAdvanced();
      return this;
    }
    /**
     *
     * @returns {Search}
     */

  }, {
    key: "showSimpleFilter",
    value: function showSimpleFilter() {
      $('.filter-wrapper form').html(Handlebars.templates.mainFilter());
      return this;
    }
    /**
     *
     * @returns {Search}
     */

  }, {
    key: "initAdvanced",
    value: function initAdvanced() {
      var d = new Date();
      $("#ranges").html("1920г. - " + d.getFullYear() + "г.");
      $("#years-from").val(1920);
      $("#years-to").val(d.getFullYear());
      $("#year-range").slider({
        range: true,
        min: 1920,
        max: d.getFullYear(),
        values: [1920, d.getFullYear()],
        slide: function slide(event, ui) {
          $("#years-from").val(ui.values[0]);
          $("#years-to").val(ui.values[1]);
          $("#ranges").html(ui.values[0] + "г. - " + ui.values[1] + "г.");
        }
      });
      return this;
    }
  }]);

  return Search;
}();
/**
 *  Application class
 */


var App = /*#__PURE__*/function () {
  /**
   * App constructor
   */
  function App() {
    _classCallCheck(this, App);

    app = this;
    this.init();
    this.layout();
    return this;
  }
  /**
   *
   * @returns {App}
   */


  _createClass(App, [{
    key: "init",
    value: function init() {
      var that = this;
      AOS.init({
        once: true
      });
      that.currentResult = null;
      this.api = new Api();
      this.navigation = new Navigation();
      this.search = new Search();
      $(document).on('click', '.grid-item a', function (e) {
        e.preventDefault();
        var parent = $(this).closest('.grid-item'),
            id = parent.data('id');

        if (that.currentResult !== null) {
          if (that.currentResult.items[id] !== undefined) {
            var item = that.currentResult.items[id];

            if (item.media_type === 'image') {
              that.loadMedia(item);
            } else {
              that.loadMedia(item);
            }
          }
        }
      });
      return this;
    }
    /**
     *
     * @returns {App}
     */

  }, {
    key: "layout",
    value: function layout() {
      $('body').html(Handlebars.templates.layout());
      this.pageContainer = $('#page-wrapper .page-container');
      this.resetResult();
      this.prepareHome();
      return this;
    }
    /**
     *
     * @returns {App}
     */

  }, {
    key: "resetResult",
    value: function resetResult() {
      this.currentResult = null;
      this.currentpage = 1;
      return this;
    }
    /**
     *
     * @returns {App}
     */

  }, {
    key: "prepareHome",
    value: function prepareHome() {
      this.pageContainer.append(Handlebars.templates.home());
      this.loadHome();
      return this;
    }
    /**
     *
     * @returns {App}
     */

  }, {
    key: "loadHome",
    value: function loadHome() {
      this.search.showSimpleFilter();

      try {
        this.api.callHomeTop(this.loadPopular.bind(this));
      } catch (error) {}

      return this;
    }
    /**
     *
     * @param result
     * @returns {App}
     */

  }, {
    key: "loadPopular",
    value: function loadPopular(result) {
      if (result === undefined || result === null) {
        throw new ValidationError('Имам проблем със заявката!');
      }

      this.resetResult();
      this.currentResult = result;
      this.setHtmlContainer(Handlebars.templates.results({
        'title': 'Избор на редактора'
      }));

      if (result.total_hits > 0) {
        this.setHtmlResult(result.items);
      } else {
        this.setHtmlContainer(Handlebars.templates.missing);
      }

      return this;
    }
    /**
     *
     * @param result
     * @returns {App}
     */

  }, {
    key: "loadResults",
    value: function loadResults(result) {
      this.resetResult();
      this.currentResult = result;
      this.setHtmlContainer(Handlebars.templates.results({
        'title': 'Резултати'
      }));

      if (result.total_hits > 0) {
        this.setHtmlResult(result.items);
      } else {
        this.setHtmlContainer(Handlebars.templates.missing);
      }

      return this;
    }
    /**
     *
     * @param result
     * @returns {App}
     */

  }, {
    key: "loadMedia",
    value: function loadMedia(result) {
      var modal = Handlebars.templates.modalMedia;
      var html = modal({
        'item': result
      });
      $('body').append(html);
      $('#mediaModal').modal('show');
      $('#mediaModal').one('hidden.bs.modal', function () {
        $(this).remove();
      });
      return this;
    }
    /**
     *
     * @param html
     * @returns {App}
     */

  }, {
    key: "setHtmlContainer",
    value: function setHtmlContainer(html) {
      this.pageContainer.find('.data-wrapper').empty();
      this.pageContainer.find('.data-wrapper').html(html);
      return this;
    }
    /**
     *
     * @param items
     * @returns {App}
     */

  }, {
    key: "setHtmlResult",
    value: function setHtmlResult(items) {
      var html = Handlebars.templates.item({
        'items': items
      });
      $('.data-wrapper .results-container').html(html);
      $('.data-wrapper').imagesLoaded(function () {
        $('.data-wrapper .placeholders').fadeOut();
        $('.data-wrapper .results-container').removeClass('d-none');
        $('.results-container').masonry({
          itemSelector: '.grid-item',
          gutter: 0
        });
        var lazy = new LazyLoad({
          elements_selector: ".lazy",
          callback_loaded: function callback_loaded() {
            $('.results-container').masonry('layout');
          }
        });
      });
      return this;
    }
  }]);

  return App;
}();

$(function () {
  new App();
});
