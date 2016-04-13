'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rSektorKur', {
                parent: 'entity',
                url: '/rSektorKurs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rSektorKur.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rSektorKur/rSektorKurs.html',
                        controller: 'RSektorKurController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rSektorKur');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rSektorKur.detail', {
                parent: 'entity',
                url: '/rSektorKur/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rSektorKur.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rSektorKur/rSektorKur-detail.html',
                        controller: 'RSektorKurDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rSektorKur');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RSektorKur', function($stateParams, RSektorKur) {
                        return RSektorKur.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rSektorKur.new', {
                parent: 'rSektorKur',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rSektorKur/rSektorKur-dialog.html',
                        controller: 'RSektorKurDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_sektor: null,
                                    uraian: null,
                                    tanaman_keras: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rSektorKur', null, { reload: true });
                    }, function() {
                        $state.go('rSektorKur');
                    })
                }]
            })
            .state('rSektorKur.edit', {
                parent: 'rSektorKur',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rSektorKur/rSektorKur-dialog.html',
                        controller: 'RSektorKurDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RSektorKur', function(RSektorKur) {
                                return RSektorKur.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rSektorKur', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rSektorKur.delete', {
                parent: 'rSektorKur',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rSektorKur/rSektorKur-delete-dialog.html',
                        controller: 'RSektorKurDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RSektorKur', function(RSektorKur) {
                                return RSektorKur.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rSektorKur', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
