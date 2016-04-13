'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rKolektibilitas', {
                parent: 'entity',
                url: '/rKolektibilitass',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rKolektibilitas.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rKolektibilitas/rKolektibilitass.html',
                        controller: 'RKolektibilitasController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rKolektibilitas');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rKolektibilitas.detail', {
                parent: 'entity',
                url: '/rKolektibilitas/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rKolektibilitas.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rKolektibilitas/rKolektibilitas-detail.html',
                        controller: 'RKolektibilitasDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rKolektibilitas');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RKolektibilitas', function($stateParams, RKolektibilitas) {
                        return RKolektibilitas.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rKolektibilitas.new', {
                parent: 'rKolektibilitas',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rKolektibilitas/rKolektibilitas-dialog.html',
                        controller: 'RKolektibilitasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_kolektibilitas: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rKolektibilitas', null, { reload: true });
                    }, function() {
                        $state.go('rKolektibilitas');
                    })
                }]
            })
            .state('rKolektibilitas.edit', {
                parent: 'rKolektibilitas',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rKolektibilitas/rKolektibilitas-dialog.html',
                        controller: 'RKolektibilitasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RKolektibilitas', function(RKolektibilitas) {
                                return RKolektibilitas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rKolektibilitas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rKolektibilitas.delete', {
                parent: 'rKolektibilitas',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rKolektibilitas/rKolektibilitas-delete-dialog.html',
                        controller: 'RKolektibilitasDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RKolektibilitas', function(RKolektibilitas) {
                                return RKolektibilitas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rKolektibilitas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
