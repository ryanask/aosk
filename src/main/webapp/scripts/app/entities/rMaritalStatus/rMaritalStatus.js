'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rMaritalStatus', {
                parent: 'entity',
                url: '/rMaritalStatuss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rMaritalStatus.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rMaritalStatus/rMaritalStatuss.html',
                        controller: 'RMaritalStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rMaritalStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rMaritalStatus.detail', {
                parent: 'entity',
                url: '/rMaritalStatus/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rMaritalStatus.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rMaritalStatus/rMaritalStatus-detail.html',
                        controller: 'RMaritalStatusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rMaritalStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RMaritalStatus', function($stateParams, RMaritalStatus) {
                        return RMaritalStatus.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rMaritalStatus.new', {
                parent: 'rMaritalStatus',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rMaritalStatus/rMaritalStatus-dialog.html',
                        controller: 'RMaritalStatusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_marital_status: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rMaritalStatus', null, { reload: true });
                    }, function() {
                        $state.go('rMaritalStatus');
                    })
                }]
            })
            .state('rMaritalStatus.edit', {
                parent: 'rMaritalStatus',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rMaritalStatus/rMaritalStatus-dialog.html',
                        controller: 'RMaritalStatusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RMaritalStatus', function(RMaritalStatus) {
                                return RMaritalStatus.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rMaritalStatus', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rMaritalStatus.delete', {
                parent: 'rMaritalStatus',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rMaritalStatus/rMaritalStatus-delete-dialog.html',
                        controller: 'RMaritalStatusDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RMaritalStatus', function(RMaritalStatus) {
                                return RMaritalStatus.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rMaritalStatus', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
