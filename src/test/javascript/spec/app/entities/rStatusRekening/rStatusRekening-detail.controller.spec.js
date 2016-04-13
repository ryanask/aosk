'use strict';

describe('Controller Tests', function() {

    describe('RStatusRekening Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRStatusRekening;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRStatusRekening = jasmine.createSpy('MockRStatusRekening');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RStatusRekening': MockRStatusRekening
            };
            createController = function() {
                $injector.get('$controller')("RStatusRekeningDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rStatusRekeningUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
