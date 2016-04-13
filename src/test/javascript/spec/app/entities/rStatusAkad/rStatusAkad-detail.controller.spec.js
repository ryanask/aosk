'use strict';

describe('Controller Tests', function() {

    describe('RStatusAkad Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRStatusAkad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRStatusAkad = jasmine.createSpy('MockRStatusAkad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RStatusAkad': MockRStatusAkad
            };
            createController = function() {
                $injector.get('$controller')("RStatusAkadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rStatusAkadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
