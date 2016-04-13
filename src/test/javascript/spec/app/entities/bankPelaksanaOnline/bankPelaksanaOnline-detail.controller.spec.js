'use strict';

describe('Controller Tests', function() {

    describe('BankPelaksanaOnline Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBankPelaksanaOnline;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBankPelaksanaOnline = jasmine.createSpy('MockBankPelaksanaOnline');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BankPelaksanaOnline': MockBankPelaksanaOnline
            };
            createController = function() {
                $injector.get('$controller')("BankPelaksanaOnlineDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:bankPelaksanaOnlineUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
