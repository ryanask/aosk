'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bankPelaksanaOnline', {
                parent: 'entity',
                url: '/bankPelaksanaOnlines',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.bankPelaksanaOnline.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankPelaksanaOnline/bankPelaksanaOnlines.html',
                        controller: 'BankPelaksanaOnlineController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankPelaksanaOnline');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bankPelaksanaOnline.detail', {
                parent: 'entity',
                url: '/bankPelaksanaOnline/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.bankPelaksanaOnline.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankPelaksanaOnline/bankPelaksanaOnline-detail.html',
                        controller: 'BankPelaksanaOnlineDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankPelaksanaOnline');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BankPelaksanaOnline', function($stateParams, BankPelaksanaOnline) {
                        return BankPelaksanaOnline.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bankPelaksanaOnline.new', {
                parent: 'bankPelaksanaOnline',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bankPelaksanaOnline/bankPelaksanaOnline-dialog.html',
                        controller: 'BankPelaksanaOnlineDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    kode_bank: null,
                                    nama_bank: null,
                                    id_aktifitas_1: null,
                                    id_aktifitas_2: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bankPelaksanaOnline', null, { reload: true });
                    }, function() {
                        $state.go('bankPelaksanaOnline');
                    })
                }]
            })
            .state('bankPelaksanaOnline.edit', {
                parent: 'bankPelaksanaOnline',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bankPelaksanaOnline/bankPelaksanaOnline-dialog.html',
                        controller: 'BankPelaksanaOnlineDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BankPelaksanaOnline', function(BankPelaksanaOnline) {
                                return BankPelaksanaOnline.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bankPelaksanaOnline', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bankPelaksanaOnline.delete', {
                parent: 'bankPelaksanaOnline',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/bankPelaksanaOnline/bankPelaksanaOnline-delete-dialog.html',
                        controller: 'BankPelaksanaOnlineDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BankPelaksanaOnline', function(BankPelaksanaOnline) {
                                return BankPelaksanaOnline.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bankPelaksanaOnline', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
