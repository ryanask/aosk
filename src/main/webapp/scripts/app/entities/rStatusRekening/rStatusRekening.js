'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rStatusRekening', {
                parent: 'entity',
                url: '/rStatusRekenings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rStatusRekening.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rStatusRekening/rStatusRekenings.html',
                        controller: 'RStatusRekeningController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rStatusRekening');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rStatusRekening.detail', {
                parent: 'entity',
                url: '/rStatusRekening/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rStatusRekening.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rStatusRekening/rStatusRekening-detail.html',
                        controller: 'RStatusRekeningDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rStatusRekening');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RStatusRekening', function($stateParams, RStatusRekening) {
                        return RStatusRekening.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rStatusRekening.new', {
                parent: 'rStatusRekening',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rStatusRekening/rStatusRekening-dialog.html',
                        controller: 'RStatusRekeningDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_status_rekening: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rStatusRekening', null, { reload: true });
                    }, function() {
                        $state.go('rStatusRekening');
                    })
                }]
            })
            .state('rStatusRekening.edit', {
                parent: 'rStatusRekening',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rStatusRekening/rStatusRekening-dialog.html',
                        controller: 'RStatusRekeningDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RStatusRekening', function(RStatusRekening) {
                                return RStatusRekening.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rStatusRekening', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rStatusRekening.delete', {
                parent: 'rStatusRekening',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rStatusRekening/rStatusRekening-delete-dialog.html',
                        controller: 'RStatusRekeningDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RStatusRekening', function(RStatusRekening) {
                                return RStatusRekening.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rStatusRekening', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
