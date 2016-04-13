'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rPendidikan', {
                parent: 'entity',
                url: '/rPendidikans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rPendidikan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rPendidikan/rPendidikans.html',
                        controller: 'RPendidikanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rPendidikan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rPendidikan.detail', {
                parent: 'entity',
                url: '/rPendidikan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rPendidikan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rPendidikan/rPendidikan-detail.html',
                        controller: 'RPendidikanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rPendidikan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RPendidikan', function($stateParams, RPendidikan) {
                        return RPendidikan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rPendidikan.new', {
                parent: 'rPendidikan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPendidikan/rPendidikan-dialog.html',
                        controller: 'RPendidikanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_pendidikan: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rPendidikan', null, { reload: true });
                    }, function() {
                        $state.go('rPendidikan');
                    })
                }]
            })
            .state('rPendidikan.edit', {
                parent: 'rPendidikan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPendidikan/rPendidikan-dialog.html',
                        controller: 'RPendidikanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RPendidikan', function(RPendidikan) {
                                return RPendidikan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rPendidikan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rPendidikan.delete', {
                parent: 'rPendidikan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rPendidikan/rPendidikan-delete-dialog.html',
                        controller: 'RPendidikanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RPendidikan', function(RPendidikan) {
                                return RPendidikan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rPendidikan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
