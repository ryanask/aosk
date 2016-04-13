'use strict';

describe('Controller Tests', function() {

    describe('RJenisKelamin Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRJenisKelamin;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRJenisKelamin = jasmine.createSpy('MockRJenisKelamin');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RJenisKelamin': MockRJenisKelamin
            };
            createController = function() {
                $injector.get('$controller')("RJenisKelaminDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rJenisKelaminUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
