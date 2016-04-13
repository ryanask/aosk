'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rPekerjaan', {
                parent: 'entity',
                url: '/rPekerjaans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rPekerjaan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rPekerjaan/rPekerjaans.html',
                        controller: 'RPekerjaanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rPekerjaan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rPekerjaan.detail', {
                parent: 'entity',
                url: '/rPekerjaan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rPekerjaan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rPekerjaan/rPekerjaan-detail.html',
                        controller: 'RPekerjaanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rPekerjaan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RPekerjaan', function($stateParams, RPekerjaan) {
                        return RPekerjaan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rPekerjaan.new', {
                parent: 'rPekerjaan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPekerjaan/rPekerjaan-dialog.html',
                        controller: 'RPekerjaanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_pekerjaan: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rPekerjaan', null, { reload: true });
                    }, function() {
                        $state.go('rPekerjaan');
                    })
                }]
            })
            .state('rPekerjaan.edit', {
                parent: 'rPekerjaan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPekerjaan/rPekerjaan-dialog.html',
                        controller: 'RPekerjaanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RPekerjaan', function(RPekerjaan) {
                                return RPekerjaan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rPekerjaan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rPekerjaan.delete', {
                parent: 'rPekerjaan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPekerjaan/rPekerjaan-delete-dialog.html',
                        controller: 'RPekerjaanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RPekerjaan', function(RPekerjaan) {
                                return RPekerjaan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rPekerjaan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
