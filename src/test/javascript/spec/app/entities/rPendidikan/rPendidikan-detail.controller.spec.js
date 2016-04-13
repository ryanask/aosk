'use strict';

describe('Controller Tests', function() {

    describe('RPendidikan Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRPendidikan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRPendidikan = jasmine.createSpy('MockRPendidikan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RPendidikan': MockRPendidikan
            };
            createController = function() {
                $injector.get('$controller')("RPendidikanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rPendidikanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
