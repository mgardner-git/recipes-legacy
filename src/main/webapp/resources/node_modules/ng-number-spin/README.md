# ng-number-spin

ng-number-spin is a customizable directive for numeric inputs with spins for [Angular.js](http://angularjs.org)

### Installation

ng-number-spin requires [Node.js](https://nodejs.org/) v4+ to run.

Install the package.

```sh
$ npm install ng-number-spin --save
```

Add to module dependencies

```sh
var app = angular.module('app', ['ngNumberSpin']);
```

Usage example

```sh
<number-spin data-ng-model="vm.amount"></number-spin>
```

### Documentation

ng-number-spin is currently supported with the following features. Options on how to use them in your own application are linked below.

| Option | Type |  Default | Description |
| ------ | ------ | --------- | --------- |
| step | number | 1 | increase/decrease step size on button click
| spin-buttons | boolean | true | show or hide spin buttons



### Development

ng-number-spin uses [Gulp](http://gulpjs.com).

To build source files use this command.

```sh
$ gulp build
```


License
----

MIT
