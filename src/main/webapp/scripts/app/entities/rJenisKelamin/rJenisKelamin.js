'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rJenisKelamin', {
                parent: 'entity',
                url: '/rJenisKelamins',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rJenisKelamin.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rJenisKelamin/rJenisKelamins.html',
                        controller: 'RJenisKelaminController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rJenisKelamin');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rJenisKelamin.detail', {
                parent: 'entity',
                url: '/rJenisKelamin/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.rJenisKelamin.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rJenisKelamin/rJenisKelamin-detail.html',
                        controller: 'RJenisKelaminDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rJenisKelamin');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RJenisKelamin', function($stateParams, RJenisKelamin) {
                        return RJenisKelamin.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rJenisKelamin.new', {
                parent: 'rJenisKelamin',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rJenisKelamin/rJenisKelamin-dialog.html',
                        controller: 'RJenisKelaminDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_jenis_kelamin: null,
                                    keterangan: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rJenisKelamin', null, { reload: true });
                    }, function() {
                        $state.go('rJenisKelamin');
                    })
                }]
            })
            .state('rJenisKelamin.edit', {
                parent: 'rJenisKelamin',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rJenisKelamin/rJenisKelamin-dialog.html',
                        controller: 'RJenisKelaminDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RJenisKelamin', function(RJenisKelamin) {
                                return RJenisKelamin.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rJenisKelamin', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rJenisKelamin.delete', {
                parent: 'rJenisKelamin',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rJenisKelamin/rJenisKelamin-delete-dialog.html',
                        controller: 'RJenisKelaminDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RJenisKelamin', function(RJenisKelamin) {
                                return RJenisKelamin.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rJenisKelamin', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
