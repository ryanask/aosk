'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rStatusAkad', {
                parent: 'entity',
                url: '/rStatusAkads',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rStatusAkad.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rStatusAkad/rStatusAkads.html',
                        controller: 'RStatusAkadController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rStatusAkad');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rStatusAkad.detail', {
                parent: 'entity',
                url: '/rStatusAkad/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rStatusAkad.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rStatusAkad/rStatusAkad-detail.html',
                        controller: 'RStatusAkadDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rStatusAkad');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RStatusAkad', function($stateParams, RStatusAkad) {
                        return RStatusAkad.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rStatusAkad.new', {
                parent: 'rStatusAkad',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rStatusAkad/rStatusAkad-dialog.html',
                        controller: 'RStatusAkadDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_status_akad: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rStatusAkad', null, { reload: true });
                    }, function() {
                        $state.go('rStatusAkad');
                    })
                }]
            })
            .state('rStatusAkad.edit', {
                parent: 'rStatusAkad',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rStatusAkad/rStatusAkad-dialog.html',
                        controller: 'RStatusAkadDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RStatusAkad', function(RStatusAkad) {
                                return RStatusAkad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rStatusAkad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rStatusAkad.delete', {
                parent: 'rStatusAkad',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rStatusAkad/rStatusAkad-delete-dialog.html',
                        controller: 'RStatusAkadDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RStatusAkad', function(RStatusAkad) {
                                return RStatusAkad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rStatusAkad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
