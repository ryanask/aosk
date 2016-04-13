'use strict';

describe('Controller Tests', function() {

    describe('RJenisAgunan Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRJenisAgunan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRJenisAgunan = jasmine.createSpy('MockRJenisAgunan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RJenisAgunan': MockRJenisAgunan
            };
            createController = function() {
                $injector.get('$controller')("RJenisAgunanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rJenisAgunanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
