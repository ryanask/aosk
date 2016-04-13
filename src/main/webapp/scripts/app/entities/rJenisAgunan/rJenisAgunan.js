'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rJenisAgunan', {
                parent: 'entity',
                url: '/rJenisAgunans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rJenisAgunan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rJenisAgunan/rJenisAgunans.html',
                        controller: 'RJenisAgunanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rJenisAgunan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rJenisAgunan.detail', {
                parent: 'entity',
                url: '/rJenisAgunan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rJenisAgunan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rJenisAgunan/rJenisAgunan-detail.html',
                        controller: 'RJenisAgunanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rJenisAgunan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RJenisAgunan', function($stateParams, RJenisAgunan) {
                        return RJenisAgunan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rJenisAgunan.new', {
                parent: 'rJenisAgunan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rJenisAgunan/rJenisAgunan-dialog.html',
                        controller: 'RJenisAgunanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_jenis_agunan: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rJenisAgunan', null, { reload: true });
                    }, function() {
                        $state.go('rJenisAgunan');
                    })
                }]
            })
            .state('rJenisAgunan.edit', {
                parent: 'rJenisAgunan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rJenisAgunan/rJenisAgunan-dialog.html',
                        controller: 'RJenisAgunanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RJenisAgunan', function(RJenisAgunan) {
                                return RJenisAgunan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rJenisAgunan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rJenisAgunan.delete', {
                parent: 'rJenisAgunan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rJenisAgunan/rJenisAgunan-delete-dialog.html',
                        controller: 'RJenisAgunanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RJenisAgunan', function(RJenisAgunan) {
                                return RJenisAgunan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rJenisAgunan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
