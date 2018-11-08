/**
 * Спиннер.
 */
var BounceLoader = VueSpinner.BounceLoader;

/**
 * Компонент отображения новости в списке.
 * @type {{props: {item: ObjectConstructor}, template: string, methods: {moment: (function(*=): *)}}}
 */
var NewsItem = {
    props: {
        item: Object
    },
    template: '<div class="row my-2 mx-0 p-0">' +
        '           <div class="col-data text-right">' +
        '               <div class="text-muted"><small>{{ moment(item.published).fromNow() }}</small></div>' +
        '           </div> ' +
        '           <div class="col-md px-1 py-0">' +
        '               <a :href="item.host + item.url" target="_blank" rel="noopener noreferrer" class="text-dark w-100 news-title py-1">{{  item.title  }}</a>' +
        '               <div class="w-100">' +
        '                   <small><a class="text-muted" target="_blank" rel="noopener noreferrer" v-bind:href="item.host">{{ item.hostName }}</a></small>' +
        '                   <small v-if="item.commentsCount > 0" >' +
        '                       <img src="../img/commentary.svg" class="icon-comments" />{{ item.commentsCount }}' +
        '                   </small>' +
        '               </div>' +
        '           </div> ' +
        '      </div>',
    methods: {
        moment: function (date) {
            return moment(date)
        }
    }
};

/**
 * Компонент отображения колонки с новостями.
 * @type {{components: {"news-item": {props: {item: ObjectConstructor}, template: string, methods: {moment: (function(*=): *)}}}, props: {categories: ObjectConstructor, column_index: NumberConstructor}, template: string, data: (function(): {news: Array, selected: StringConstructor}), computed: {change_category: {get: (function(): *), set: NewsColumn.computed.change_category.set}}, mounted, methods: {load_category: NewsColumn.methods.load_category}, watch: {selected}}}
 */
var NewsColumn = {
    components: {
        'news-item': NewsItem,
        'bounce-loader' :BounceLoader
    },
    props: {
        categories: Object,
        column_index: Number
    },
    template:'  <div class="col-md p-0 m-0">' +
        '                <div class="w-100 p-0 bg-dark text-white"> ' +
        '                    <img v-bind:src="get_icon" class="float-left news-category-icon">' +
        '                    <select class="form-control bg-dark text-white border-0 w-auto p-0"  v-model="change_category"> ' +
        '                        <option v-for="(val,key, ind) in categories" v-bind:value="key">' +
        '                            {{ val }}' +
        '                        </option>' +
        '                    </select>' +
        '                </div>' +
        '                <bounce-loader :loading="loading" :color="loader_color" :size="loader_size" class="spinner"></bounce-loader>          \n' +
        '                <div class="news-container">' +
        '                   <news-item v-for="(item, index) in news" :item="item" v-bind:key="item.id" />' +
        '               </div>' +
        '       </div>',
    data:function() {
        return {
            news : [],
            selected: 'ALL',
            loading: true,
            loader_size: '40px',
            loader_color: '#CCCCCC'
        }
    },
    computed: {
        change_category: {
            get: function () {
                return this.selected;
            },
            set: function (newValue) {
                this.selected = newValue;
                this.load_category(this.selected);
            }
        },
        get_icon: function () {
            return '../img/' + this.selected.toLowerCase() + '.svg';
        }
    },
    mounted(){
        var stored_category = localStorage.getItem('column'+this.column_index);
        this.selected = (stored_category) ? stored_category : 'ALL';
        this.load_category(this.selected);
    },
    methods: {
        load_category: function(category){
            axios
                .get('/api/news/'+ category)
                .then(response => {
                    this.news = response.data;
                    this.loading = false});
        }
    },
    watch: {
        selected(newValue) {
            localStorage.setItem('column'+this.column_index, newValue)
        }
    }
};

/**
 * Страница с новостями.
 * @type {{components: {"news-column": {components: {"news-item": {props: {item: ObjectConstructor}, template: string, methods: {moment: (function(*=): *)}}}, props: {categories: ObjectConstructor, column_index: NumberConstructor}, template: string, data: (function(): {news: Array, selected: StringConstructor}), computed: {change_category: {get: (function(): *), set: NewsColumn.computed.change_category.set}}, mounted, methods: {load_category: NewsColumn.methods.load_category}, watch: {selected}}}, data: (function(): {column_count: number, categories: null}), template: string, watch: {column_count}, mounted}}
 */
var NewsPage = {
    components: {
        'news-column': NewsColumn
    },
    data: function() {
        return {
            column_count: 3,
            categories: null
        }
    },
    template: '<div class="row d-flex flex-fill m-0">' +
        '<div class="navbar news-container-btns px-0">\n' +
        '                    <a  class="nav-item nav-link text-white px-2" href="#"' +
        '                        v-if="column_count > 1"' +
        '                        v-on:click="column_count--">-</a>\n' +
        '                    <a  class="nav-item nav-link text-white px-2" href="#"' +
        '                        v-if="column_count < ((categories) ? Object.keys(categories).length : 6)"' +
        '                        v-on:click="column_count++">+</a>\n' +
        '                </div>' +
        '<news-column  ' +
        '                         v-bind:categories="categories"' +
        '                         v-bind:column_index="n"' +
        '                         v-bind:key="n"' +
        '                         v-for="n in column_count" /></div>',
    watch: {
        column_count(newValue) {
            localStorage.column_count = newValue
        }
    },
    mounted() {
        if (localStorage.column_count) {
            this.column_count = parseInt(localStorage.column_count)
        }
        axios
            .get('/api/cats')
            .then(response => (this.categories= response.data))
    }
};

/**
 * Компонент страницы "О нас".
 * @type {{template: string}}
 */
var AboutPage = {
    data: function(){
        return {
            about: ''
        }
    },
    template: '<div class="p-5" v-html="about"></div>',
    watch: {
        about(newValue) {
            localStorage.about = newValue
        }
    },
    mounted() {
        if (localStorage.about) {
            this.about = parseInt(localStorage.about)
        }
        axios
            .get('/api/about')
            .then(response => (this.about= response.data))
    }
};

/**
 * Зарегистрированные маршруты.
 * @type {*[]}
 */
var routes = [
    {path: '/', component: NewsPage},
    {path: '/about', component: AboutPage}
];

var router = new VueRouter({routes});

var app = new Vue({
    router,
    el: '#app',
    components: {
        'page_news': NewsPage,
        'page_about': AboutPage
    },
    methods: {
        getCookie (name) {
            const match = document.cookie.match(new RegExp(name + '=([^;]+)'));
            if (match) return match[1];
            return
        }
    },
    created() {
        const lang = this.getCookie('lang');
        if (lang) {
            moment.locale(lang);
        } else {
            moment.locale('ru');
        }
    }
});