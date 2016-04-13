'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rPengikatanAgunan', {
                parent: 'entity',
                url: '/rPengikatanAgunans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rPengikatanAgunan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rPengikatanAgunan/rPengikatanAgunans.html',
                        controller: 'RPengikatanAgunanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rPengikatanAgunan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rPengikatanAgunan.detail', {
                parent: 'entity',
                url: '/rPengikatanAgunan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rPengikatanAgunan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rPengikatanAgunan/rPengikatanAgunan-detail.html',
                        controller: 'RPengikatanAgunanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rPengikatanAgunan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RPengikatanAgunan', function($stateParams, RPengikatanAgunan) {
                        return RPengikatanAgunan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rPengikatanAgunan.new', {
                parent: 'rPengikatanAgunan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPengikatanAgunan/rPengikatanAgunan-dialog.html',
                        controller: 'RPengikatanAgunanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_pengikatan_agunan: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rPengikatanAgunan', null, { reload: true });
                    }, function() {
                        $state.go('rPengikatanAgunan');
                    })
                }]
            })
            .state('rPengikatanAgunan.edit', {
                parent: 'rPengikatanAgunan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPengikatanAgunan/rPengikatanAgunan-dialog.html',
                        controller: 'RPengikatanAgunanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RPengikatanAgunan', function(RPengikatanAgunan) {
                                return RPengikatanAgunan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rPengikatanAgunan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rPengikatanAgunan.delete', {
                parent: 'rPengikatanAgunan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPengikatanAgunan/rPengikatanAgunan-delete-dialog.html',
                        controller: 'RPengikatanAgunanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RPengikatanAgunan', function(RPengikatanAgunan) {
                                return RPengikatanAgunan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rPengikatanAgunan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
