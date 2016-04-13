'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rNegaraTujuan', {
                parent: 'entity',
                url: '/rNegaraTujuans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rNegaraTujuan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rNegaraTujuan/rNegaraTujuans.html',
                        controller: 'RNegaraTujuanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rNegaraTujuan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rNegaraTujuan.detail', {
                parent: 'entity',
                url: '/rNegaraTujuan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rNegaraTujuan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rNegaraTujuan/rNegaraTujuan-detail.html',
                        controller: 'RNegaraTujuanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rNegaraTujuan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RNegaraTujuan', function($stateParams, RNegaraTujuan) {
                        return RNegaraTujuan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rNegaraTujuan.new', {
                parent: 'rNegaraTujuan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rNegaraTujuan/rNegaraTujuan-dialog.html',
                        controller: 'RNegaraTujuanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_negara_tujuan: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rNegaraTujuan', null, { reload: true });
                    }, function() {
                        $state.go('rNegaraTujuan');
                    })
                }]
            })
            .state('rNegaraTujuan.edit', {
                parent: 'rNegaraTujuan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rNegaraTujuan/rNegaraTujuan-dialog.html',
                        controller: 'RNegaraTujuanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RNegaraTujuan', function(RNegaraTujuan) {
                                return RNegaraTujuan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rNegaraTujuan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rNegaraTujuan.delete', {
                parent: 'rNegaraTujuan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rNegaraTujuan/rNegaraTujuan-delete-dialog.html',
                        controller: 'RNegaraTujuanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RNegaraTujuan', function(RNegaraTujuan) {
                                return RNegaraTujuan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rNegaraTujuan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
