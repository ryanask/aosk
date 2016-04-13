'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rKantor', {
                parent: 'entity',
                url: '/rKantors',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rKantor.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rKantor/rKantors.html',
                        controller: 'RKantorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rKantor');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rKantor.detail', {
                parent: 'entity',
                url: '/rKantor/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rKantor.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rKantor/rKantor-detail.html',
                        controller: 'RKantorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rKantor');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RKantor', function($stateParams, RKantor) {
                        return RKantor.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rKantor.new', {
                parent: 'rKantor',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rKantor/rKantor-dialog.html',
                        controller: 'RKantorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_kantor: null,
                                    kantor: null,
                                    alamat: null,
                                    telepon: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rKantor', null, { reload: true });
                    }, function() {
                        $state.go('rKantor');
                    })
                }]
            })
            .state('rKantor.edit', {
                parent: 'rKantor',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rKantor/rKantor-dialog.html',
                        controller: 'RKantorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RKantor', function(RKantor) {
                                return RKantor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rKantor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rKantor.delete', {
                parent: 'rKantor',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rKantor/rKantor-delete-dialog.html',
                        controller: 'RKantorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RKantor', function(RKantor) {
                                return RKantor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rKantor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
